#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Common\Structures.mqh>
#include <Common\Utils.mqh>
#include <Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Provide positions as JSON                                        |
//+------------------------------------------------------------------+
class PositionProvider
  {
   long                   _magic;
   string                 _position_pattern;
   CPositionInfo *        position_info;
   CHistoryPositionInfo * history_info;
public:

                     PositionProvider(long magic)
     {
      _magic = magic;
      position_info = PositionInfo();
      history_info = HistoryPositionInfo();
      _position_pattern = "{ \"isHistory\": %s, \"type\": %s, \"magic\": %.0f, \"positionId\": %.0f, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f }";
     }
                    ~PositionProvider() {}

public:
   string            GetPositions();
private:
   void              FetchPositions(Position &positions[]);
   void              FetchOpenPositions(Position &positions[]);
   void              FetchHistory(Position &positions[]);
   void              AddPosition(Position &position, Position &positions[]);
  };

//+------------------------------------------------------------------+
//| Get all positions from history and open in one JSON array        |
//+------------------------------------------------------------------+
string::PositionProvider            GetPositions()
  {
   Position positions[];
   FetchPositions(positions);
   string items = "";
   int size = ArraySize(positions);
   for(int i = 0; i < size; i++)
     {
      string item = StringFormat(_position_pattern,
                                 BoolToString(positions[i].isHistory), PositionTypeToString(positions[i].type),
                                 positions[i].magic, positions[i].positionId, positions[i].lot, positions[i].stopLoss,
                                 positions[i].takeProfit, positions[i].openAt, positions[i].closeAt, positions[i].profit);
      items += item;
      if(i != size - 1)
         items += ", ";
     }

   return "[" + items + "]";
  }

//+------------------------------------------------------------------+
//| Collect all positions from history and currently open            |
//+------------------------------------------------------------------+
void::PositionProvider              FetchPositions(Position &positions[])
  {
   FetchOpenPositions(positions);
   FetchHistory(positions);
  }

//+------------------------------------------------------------------+
//| Fetch currently open positions                                   |
//+------------------------------------------------------------------+
void::PositionProvider              FetchOpenPositions(Position &positions[])
  {
   position_info.HistorySelect(0, TimeCurrent());
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Magic() == _magic)
           {
            Position position;
            position.isHistory = false;
            position.openAt = position_info.TimeMsc();
            position.positionId = position_info.Ticket();
            position.openPrice = position_info.PriceOpen();
            position.swap = NormalizeDouble(position_info.Swap());
            position.lot = NormalizeDouble(position_info.Volume());
            position.profit = NormalizeDouble(position_info.Profit());
            position.stopLoss = StopLossToPoint(position_info.StopLoss(),
                                                position_info.PriceOpen(),
                                                position_info.PositionType());
            position.takeProfit = TakeProfitToPoint(position_info.TakeProfit(),
                                                    position_info.PriceOpen(),
                                                    position_info.PositionType());
            position.commission = NormalizeDouble(position_info.Commission());
            position.type = position_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
            AddPosition(position, positions);
           }
  }

//+------------------------------------------------------------------+
//| Fetch history of closed positions                                |
//+------------------------------------------------------------------+
void::PositionProvider              FetchHistory(Position &positions[])
  {
   history_info.HistorySelect(0, TimeCurrent());
   int total = history_info.PositionsTotal();
   for(int i = 0; i < total; i++)
     {
      //--- Select a closed position by its index in the list
      if(history_info.SelectByIndex(i))
        {
         Position position;
         position.isHistory = true;
         position.magic = _magic;
         position.openPrice = history_info.PriceOpen();
         position.closePrice = history_info.PriceClose();
         position.positionId = history_info.Ticket();
         position.openAt = history_info.TimeOpenMsc();
         position.closeAt = history_info.TimeCloseMsc();
         position.swap = NormalizeDouble(history_info.Swap(), 2);
         position.lot = NormalizeDouble(history_info.Volume(), 2);
         position.profit = NormalizeDouble(history_info.Profit(), 2);
         position.stopLoss = StopLossToPoint(history_info.StopLoss());
         position.takeProfit = TakeProfitToPoint(history_info.TakeProfit());
         position.commission = NormalizeDouble(history_info.Commission(), 2);
         position.type = history_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
         AddPosition(position, positions);
        }
     }
  }

//+------------------------------------------------------------------+
//| Add position to array                                            |
//+------------------------------------------------------------------+
void::PositionProvider              AddPosition(Position &position, Position &positions[])
  {
   int size = ArraySize(positions);
   ArrayResize(positions, size + 1);
   positions[size] = position;
  }
//+------------------------------------------------------------------+