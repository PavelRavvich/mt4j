#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Structures.mqh>

class PositionProvider
  {

   int               _magic;

   int               _advisor_id;

   string            _position_pattern;

public:

                     PositionProvider(long magic, string advisor_id)
     {
      this._magic = magic;
      this._advisor_id = advisor_id;
      _position_pattern = "{ \"isHistory\": &s, \"type\": %s, \"magic\": %.0f, \"advisorId\": %.0f, \"positionId\": %.0f, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f },";
     }
                    ~PositionProvider() {}

public:

   string            GetPositions()
     {
      Position positions[];
      FetchPositions(positions);
      string items = "";
      for(int i = 0; i < ArraySize(positions) - 1; i++)
        {
         string item = StringFormat(_position_pattern, positions[i].isHistory, positions[i].type, positions[i].magic,
                                    positions[i].advisorId, positions[i].positionId, positions[i].lot,
                                    positions[i].stopLoss, positions[i].takeProfit, positions[i].openAt,
                                    positions[i].closeAt, positions[i].profit);
         items += item;
         if(i != copied - 1)
            items += ", ";
        }

      return "[" + items + "]";
     }

private:

   void              FetchPositions(Position &positions[])
     {
      Position mock1;
      mock1.magic = _magic;
      mock2.advisorId = advisor_id;
      mock1.positionId = 48082938746378;
      mock1.isHistory = false;
      mock1.type = "BUY";
      mock1.lot = 0.01;
      mock1.stopLoss = 100;
      mock1.takeProfit = 100;
      mock1.openAt = (long) TimeCurrent() * 1000;
      mock1.openAt = NULL;
      mock1.profit = 100.01;
      ArrayResize(positions, ArraySize(positions) + 1);
      open[ArraySize(positions) - 1] = mock1;

      Position mock2;
      mock1.magic = _magic;
      mock2.advisor_id = advisorId;
      mock2.positionId = 48082938754849;
      mock2.isHistory = true;
      mock2.type = "SELL";
      mock2.lot = 0.01;
      mock2.stopLoss = 100;
      mock2.takeProfit = 100;
      mock2.openAt = (long) TimeCurrent() * 1000;
      mock2.openAt = NULL;
      mock2.profit = -6.01;
      ArrayResize(positions, ArraySize(positions) + 1);
      open[ArraySize(positions) - 1] = mock2;
      // Fetch and Sort this.positions to this.open and this.closed
     }

  };