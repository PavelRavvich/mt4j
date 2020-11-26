#property strict

#include <Trade\PositionInfo.mqh>
#include <Common\Structures.mqh>
#include <Common\Utils.mqh>
#include <Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Provide positions as JSON                                        |
//+------------------------------------------------------------------+
class PositionProvider
  {
   long              _magic;
   string            _position_pattern;
   CPositionInfo     _position_info;
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
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Magic() == _magic)
           {
            Position position;
            position.isHistory = false;
            position.swap = position_info.Swap();
            position.lot = position_info.Volume();
            position.profit = position_info.Profit();
            position.openAt = position_info.TimeMsc();
            position.positionId = position_info.Ticket();
            position.type = position_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
            position.stopLoss = StopLossToPoint(position_info.StopLoss(), position_info.PositionType());
            position.takeProfit = TakeProfitToPoint(position_info.TakeProfit(), position_info.PositionType());
            AddPosition(position, positions);
           }
  }

//+------------------------------------------------------------------+
//| Fetch history of closed positions                                |
//+------------------------------------------------------------------+
void::PositionProvider              FetchHistory(Position &positions[])
  {
// todo fetch history
   // Position position;
   // position.isHistory = true;
   // AddPosition(position, positions);
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