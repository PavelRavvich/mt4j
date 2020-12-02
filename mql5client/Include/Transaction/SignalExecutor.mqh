#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Common\Structures.mqh>
#include <Common\Utils.mqh>
#include <Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Execute all trade signals                                        |
//+------------------------------------------------------------------+
class SignalExecutor
  {
   CTrade *          trade;
   CSymbolInfo *     symbol_info;
   CPositionInfo *   position_info;
public:
                     SignalExecutor()
     {
      trade = Trade();
      symbol_info = SymbolInfo();
      position_info = PositionInfo();
     }
                    ~SignalExecutor() { delete trade; delete symbol_info; delete position_info; }
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
void SignalExecutor::Execute(Signal &signals[])
  {
   for(int i = 0; i < ArraySize(signals); i++)
     {
      Signal signal = signals[i];
      if(signal.type == BUY)
         Buy(signal);
      if(signal.type == SELL)
         Sell(signal);
      if(signal.type == UPDATE)
         Update(signal);
      if(signal.type == CLOSE)
         Close(signal);
     }
  }

//+------------------------------------------------------------------+
//| Execute BUY                                                      |
//+------------------------------------------------------------------+
void SignalExecutor::Buy(Signal &signal)
  {
   double ask = Ask();
   while(!trade.Buy(signal.lot, symbol_info.Name(), ask,
                    StopLossToPrice(signal.stopLoss, ask, POSITION_TYPE_BUY),
                    TakeProfitToPrice(signal.takeProfit, ask, POSITION_TYPE_BUY)))
     { Print(GetLastError());}
  }
//+------------------------------------------------------------------+
//| Execute SELL                                                     |
//+------------------------------------------------------------------+
void SignalExecutor::Sell(Signal &signal)
  {
   double bid = Bid();
   while(!trade.Sell(signal.lot, symbol_info.Name(), bid,
                     StopLossToPrice(signal.stopLoss, bid, POSITION_TYPE_SELL),
                     TakeProfitToPrice(signal.takeProfit, bid, POSITION_TYPE_SELL)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute UPDATE                                                     |
//+------------------------------------------------------------------+
void SignalExecutor::Update(Signal &signal)
  {
   double openPrice = 0;
   ENUM_POSITION_TYPE type;
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Ticket() == signal.positionId)
           {
            openPrice = position_info.PriceOpen();
            type = position_info.PositionType();
            break;
           }
   if(openPrice == 0)
      return;


   while(!trade.PositionModify(signal.positionId,
                               StopLossToPrice(signal.stopLoss, openPrice, type),
                               TakeProfitToPrice(signal.takeProfit, openPrice, type)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute CLOSE by ticket(Signal.positionId)                       |
//+------------------------------------------------------------------+
void SignalExecutor::Close(Signal &signal)
  {
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Ticket() == signal.positionId)
            while(!trade.PositionClose(position_info.Ticket(), 100))
               Print(GetLastError());
  }
