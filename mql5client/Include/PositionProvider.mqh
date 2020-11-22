#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Enums.mqh>
#include <Utils.mqh>
#include <Structures.mqh>
#include <Trade\PositionInfo.mqh>


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

   string            GetPositions()
     {
      Position positions[];
      FetchPositions(_magic, positions);
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

private:

   void              FetchPositions(long magic, Position &positions[])
     {
      FetchOpenPositions(Position positions);
     }

   void              FetchOpenPositions(Position &positions[])
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
               position.stopLoss = position_info.StopLoss();
               position.takeProfit = position_info.TakeProfit();
               position.type = position_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
               AddPosition(position, positions);
              }
     }

   void              FetchHistory(Position &positions)
     {
      // todo fetch history
      Position position;
      position.isHistory = true;
      positions.AddPosition(position);
     }

   void              AddPosition(Position &position, Position &positions[])
     {
      int size = ArraySize(positions);
      ArrayResize(positions, size + 1);
      arr[size] = position;
     }

  };