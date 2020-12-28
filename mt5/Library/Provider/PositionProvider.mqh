#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Provide positions as JSON                                        |
//+------------------------------------------------------------------+
class CPositionProvider
  {
private:
   CHistoryPositionInfo * history_info;
   CPositionInfo *        position_info;
   string                 position_pattern;
public:
                     CPositionProvider(void);
                    ~CPositionProvider(void) {}
public:
   string            GetPositions(void);
private:
   void              FetchPositions(Position &positions[]);
   void              FetchOpenPositions(Position &positions[]);
   void              FetchHistory(Position &positions[]);
   void              AddPosition(Position &position, Position &positions[]);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CPositionProvider::CPositionProvider(void)
  {
   history_info = HistoryInfo();
   position_info = PositionInfo();
   position_pattern = "{ \"isHistory\": %s, \"type\": \"%s\", \"magic\": %.0f, \"positionId\": %.0f, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f }";
  }
//+------------------------------------------------------------------+
//| Get all positions from history and open in one JSON array        |
//+------------------------------------------------------------------+
string CPositionProvider::GetPositions(void)
  {
   Position positions[];
   FetchPositions(positions);
   string items = "";
   int size = ArraySize(positions);
   for(int i = 0; i < size; i++)
     {
      string item = StringFormat(position_pattern,
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
void CPositionProvider::FetchPositions(Position &positions[])
  {
   FetchOpenPositions(positions);
   FetchHistory(positions);
  }
//+------------------------------------------------------------------+
//| Fetch currently open positions                                   |
//+------------------------------------------------------------------+
void CPositionProvider::FetchOpenPositions(Position &positions[])
  {
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i) && position_info.Magic() == Magic())
        {
         Position position;
         position.isHistory = false;
         position.openAt = position_info.TimeMsc();
         position.positionId = position_info.Ticket();
         position.openPrice = position_info.PriceOpen();
         position.swap = NormalizeDouble(position_info.Swap(), 2);
         position.lot = NormalizeDouble(position_info.Volume(), 2);
         position.profit = NormalizeDouble(position_info.Profit(), 2);
         position.stopLoss = StopLossToPoint(position_info.StopLoss(),
                                             position_info.PriceOpen(),
                                             position_info.PositionType());
         position.takeProfit = TakeProfitToPoint(position_info.TakeProfit(),
                                                 position_info.PriceOpen(),
                                                 position_info.PositionType());
         position.commission = NormalizeDouble(position_info.Commission(), 2);
         position.type = position_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
         AddPosition(position, positions);
        }
  }
//+------------------------------------------------------------------+
//| Fetch history of closed positions                                |
//+------------------------------------------------------------------+
void CPositionProvider::FetchHistory(Position &positions[])
  {
   history_info.HistorySelect(0, TimeCurrent());
   int total = history_info.PositionsTotal();
   for(int i = 0; i < total; i++)
     {
      if(history_info.SelectByIndex(i) && history_info.Magic() == Magic())
        {
         Position position;
         position.isHistory = true;
         position.magic = Magic();
         position.openPrice = history_info.PriceOpen();
         position.closePrice = history_info.PriceClose();
         position.positionId = history_info.Ticket();
         position.openAt = history_info.TimeOpenMsc();
         position.closeAt = history_info.TimeCloseMsc();
         position.swap = NormalizeDouble(history_info.Swap(), 2);
         position.lot = NormalizeDouble(history_info.Volume(), 2);
         position.profit = NormalizeDouble(history_info.Profit(), 2);
         position.commission = NormalizeDouble(history_info.Commission(), 2);
         position.type = history_info.PositionType() == POSITION_TYPE_BUY ? LONG : SHORT;
         position.stopLoss = StopLossToPoint(history_info.StopLoss(), history_info.PriceOpen(), history_info.PositionType());
         position.takeProfit = TakeProfitToPoint(history_info.TakeProfit(), history_info.PriceOpen(), history_info.PositionType());
         AddPosition(position, positions);
        }
     }
  }
//+------------------------------------------------------------------+
//| Add position to array                                            |
//+------------------------------------------------------------------+
void CPositionProvider::AddPosition(Position &position, Position &positions[])
  {
   int size = ArraySize(positions);
   ArrayResize(positions, size + 1);
   positions[size] = position;
  }
//+------------------------------------------------------------------+