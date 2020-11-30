#property strict

#include <Libs\HistoryPositionInfo.mqh>
#include <Trade\PositionInfo.mqh>
#include <Common\Structures.mqh>
#include <Common\Utils.mqh>
#include <Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Provide positions as JSON                                        |
//+------------------------------------------------------------------+
class PositionProvider
  {
   long                 _magic;
   string               _position_pattern;
   CPositionInfo        _position_info;
   CHistoryPositionInfo _history_info;
public:

                     PositionProvider(long magic)
     { _magic = magic; _position_pattern = "{ \"isHistory\": %s, \"type\": %s, \"magic\": %.0f, \"positionId\": %.0f, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f }"; }
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
   _position_info.HistorySelect(0, TimeCurrent());
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(_position_info.SelectByIndex(i))
         if(_position_info.Magic() == _magic)
           {
            Position position;
            position.isHistory = false;
            position.openAt = _position_info.TimeMsc();
            position.positionId = _position_info.Ticket();
            position.openPrice = _position_info.PriceOpen();
            position.swap = NormalizeDouble(_position_info.Swap());
            position.lot = NormalizeDouble(_position_info.Volume());
            position.profit = NormalizeDouble(_position_info.Profit());
            position.stopLoss = StopLossToPoint(_position_info.StopLoss(),
                                                _position_info.PriceOpen(),
                                                _position_info.PositionType());
            position.takeProfit = TakeProfitToPoint(_position_info.TakeProfit(),
                                                    _position_info.PriceOpen(),
                                                    _position_info.PositionType());
            position.commission = NormalizeDouble(_position_info.Commission());
            position.type = _position_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
            AddPosition(position, positions);
           }
  }

//+------------------------------------------------------------------+
//| Fetch history of closed positions                                |
//+------------------------------------------------------------------+
void::PositionProvider              FetchHistory(Position &positions[])
  {
   _history_info.HistorySelect(0, TimeCurrent());
   int total = _history_info.PositionsTotal();
   for(int i = 0; i < total; i++)
     {
      //--- Select a closed position by its index in the list
      if(_history_info.SelectByIndex(i))
        {
         Position position;
         position.isHistory = true;
         position.magic = _magic;
         position.openPrice = _history_info.PriceOpen();
         position.closePrice = _history_info.PriceClose();
         position.positionId = _history_info.Ticket();
         position.openAt = _history_info.TimeOpenMsc();
         position.closeAt = _history_info.TimeCloseMsc();
         position.swap = NormalizeDouble(_history_info.Swap(), 2);
         position.lot = NormalizeDouble(_history_info.Volume(), 2);
         position.profit = NormalizeDouble(_history_info.Profit(), 2);
         position.stopLoss = StopLossToPoint(_history_info.StopLoss());
         position.takeProfit = TakeProfitToPoint(_history_info.TakeProfit());
         position.commission = NormalizeDouble(_history_info.Commission(), 2);
         position.type = _history_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
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