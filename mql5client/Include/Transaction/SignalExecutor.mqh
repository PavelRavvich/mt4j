#property strict

#include <Trade\SymbolInfo.mqh>
#include <Trade\Trade.mqh>
#include <Common\Structures.mqh>
#include <Common\Utils.mqh>
#include <Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Execute all trade signals                                        |
//+------------------------------------------------------------------+
class SignalExecutor
  {
   CTrade *          _trade;
   CSymbolInfo *     _symbolInfo;
public:
                     SignalExecutor(long magic, string symbol)
     {
      _symbolInfo = new CSymbolInfo();
      _symbolInfo.Name(symbol);
      _trade = new Trade();
      _trade.SetExpertMagicNumber(magic);
      _trade.SetTypeFillingBySymbol(symbol);
      if(SymbolInfoInteger(_symbolInfo.Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_FOK)
         _trade.SetTypeFilling(ORDER_FILLING_FOK);
      else
         if(SymbolInfoInteger(_symbolInfo.Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_IOC)
            _trade.SetTypeFilling(ORDER_FILLING_IOC);
         else
            _trade.SetTypeFilling(ORDER_FILLING_RETURN);
     }
                    ~SignalExecutor() { delete _trade; delete _symbolInfo; }
public:
   void              Execute(Signal &signal);
private:
   void              Buy(Signal &signal);
   void              Sell(Signal &signal);
   void              Close(Signal &signal);
  };

//+------------------------------------------------------------------+
//| Executing all signals                                            |
//+------------------------------------------------------------------+
void::SignalExecutor              Execute(Signal &signal)
  {
   if(signal.type == NO_ACTION)
      return;
   if(signal.type == BUY)
      Buy(signal);
   if(signal.type == SELL)
      Sell(signal);
   if(signal.type == CLOSE)
      Close(signal);
  }

//+------------------------------------------------------------------+
//| Execute BUY                                                      |
//+------------------------------------------------------------------+
void::SignalExecutor              Buy(Signal &signal)
  {
   while(!_trade.Buy(GetLot(), _symbolInfo.Symbol(), Ask(),
                     StopLossToPrice(signal.stopLoss, signal.type),
                     TakeProfitToPrice(signal.takeProfit, signal.type)))
     { Print(GetLastError());}
  }
//+------------------------------------------------------------------+
//| Execute SELL                                                     |
//+------------------------------------------------------------------+
void::SignalExecutor              Sell(Signal &signal)
  {
   while(!_trade.Sell(GetLot(), _symbolInfo.Symbol(), Bid(),
                      ConvertStopLoss(signal.stopLoss, signal.type),
                      ConvertTakeProfit(signal.takeProfit, signal.type)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute CLOSE by ticket(Signal.positionId)                       |
//+------------------------------------------------------------------+
void::SignalExecutor              Close(Signal &signal)
  {
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(position_info.SelectByIndex(i))
         if(position_info.Ticket() == signal.positionId)
            while(!trade.PositionClose(position_info.Ticket(), 100))
               Print(GetLastError());
  }
//+------------------------------------------------------------------+