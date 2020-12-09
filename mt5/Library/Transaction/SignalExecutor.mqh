#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Execute all trade signals                                        |
//+------------------------------------------------------------------+
class CSignalExecutor
  {
   CTrade *          trade;
   CSymbolInfo *     symbol_info;
   CPositionInfo *   position_info;
public:
                     CSignalExecutor()
     {
      trade = Trade();
      symbol_info = SymbolInfo();
      position_info = PositionInfo();
     }
                    ~CSignalExecutor() {}
public:
   void              Execute(Signal &signals[]);
private:
   void              Buy(Signal &signal);
   void              Sell(Signal &signal);
   void              Close(Signal &signal);
   void              Update(Signal &signal);
  };

//+------------------------------------------------------------------+
//| Executing all signals                                            |
//+------------------------------------------------------------------+
void CSignalExecutor::Execute(Signal &signals[])
  {
   for(int i = 0; i < ArraySize(signals); i++)
     {
      Signal signal = signals[i];
      if(signal.type == "BUY")
         Buy(signal);
      if(signal.type == "SELL")
         Sell(signal);
      if(signal.type == "UPDATE")
         Update(signal);
      if(signal.type == "CLOSE")
         Close(signal);
     }
  }

//+------------------------------------------------------------------+
//| Execute BUY                                                      |
//+------------------------------------------------------------------+
void CSignalExecutor::Buy(Signal &signal)
  {
   double ask = Ask();
   int counter = 0;
   while(counter++ < RetryPolicy() &&
         !trade.Buy(signal.lot, symbol_info.Name(), ask,
                    StopLossToPrice(signal.stopLoss, ask, POSITION_TYPE_BUY),
                    TakeProfitToPrice(signal.takeProfit, ask, POSITION_TYPE_BUY)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute SELL                                                     |
//+------------------------------------------------------------------+
void CSignalExecutor::Sell(Signal &signal)
  {
   double bid = Bid();
   int counter = 0;
   while(counter++ < RetryPolicy() &&
         !trade.Sell(signal.lot, symbol_info.Name(), bid,
                     StopLossToPrice(signal.stopLoss, bid, POSITION_TYPE_SELL),
                     TakeProfitToPrice(signal.takeProfit, bid, POSITION_TYPE_SELL)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute UPDATE                                                     |
//+------------------------------------------------------------------+
void CSignalExecutor::Update(Signal &signal)
  {
   double openPrice = -1;
   ENUM_POSITION_TYPE type = -1;
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Ticket() == signal.positionId)
           {
            openPrice = position_info.PriceOpen();
            type = position_info.PositionType();
            break;
           }
   if(openPrice == -1 || type == -1)
      return;

   int counter = 0;
   while(counter++ < RetryPolicy() &&
         !trade.PositionModify(signal.positionId,
                     StopLossToPrice(signal.stopLoss, openPrice, type),
                     TakeProfitToPrice(signal.takeProfit, openPrice, type)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute CLOSE                                                    |
//+------------------------------------------------------------------+
void CSignalExecutor::Close(Signal &signal)
  {
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Ticket() == signal.positionId)
           {
            int counter = 0;
            while(counter++ < RetryPolicy() && !trade.PositionClose(position_info.Ticket(), 100))
               Print(GetLastError());
           }
  }
