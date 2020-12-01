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
   CPositionInfo *   _position_info;
public:
                     SignalExecutor(long magic, string symbol)
     {
      _position_info = new CPositionInfo();
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
                    ~SignalExecutor() { delete _trade; delete _symbolInfo; delete _position_info; }
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
void::SignalExecutor              Execute(Signal &signals[])
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
void::SignalExecutor              Buy(Signal &signal)
  {
   double ask = Ask();
   while(!_trade.Buy(GetLot(), _symbolInfo.Symbol(), ask,
                     StopLossToPrice(signal.stopLoss, ask, signal.type),
                     TakeProfitToPrice(signal.takeProfit, ask, signal.type)))
     { Print(GetLastError());}
  }
//+------------------------------------------------------------------+
//| Execute SELL                                                     |
//+------------------------------------------------------------------+
void::SignalExecutor              Sell(Signal &signal)
  {
   double bid = Bid();
   while(!_trade.Sell(GetLot(), _symbolInfo.Symbol(), bid,
                      StopLossToPrice(signal.stopLoss, bid, signal.type),
                      TakeProfitToPrice(signal.takeProfit, bid, signal.type)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute UPDATE                                                     |
//+------------------------------------------------------------------+
void::SignalExecutor              Update(Signal &signal)
  {
   double openPrice = 0;
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(_position_info.SelectByIndex(i))
         if(_position_info.Ticket() == signal.positionId)
            {
             openPrice = _position_info.PriceOpen();
             break;
            }
   if(openPrice == 0)
      return;

   while(!_trade.PositionModify(signal.positionId,
                                ConvertStopLoss(signal.stopLoss, openPrice, signal.type),
                                ConvertTakeProfit(signal.takeProfit, openPrice, signal.type)))
     { Print(GetLastError()); }
  }
//+------------------------------------------------------------------+
//| Execute CLOSE by ticket(Signal.positionId)                       |
//+------------------------------------------------------------------+
void::SignalExecutor              Close(Signal &signal)
  {
   for(int i = PositionsTotal() - 1; i >= 0; i--)
      if(_position_info.SelectByIndex(i))
         if(_position_info.Ticket() == signal.positionId)
            while(!_trade.PositionClose(position_info.Ticket(), 100))
               Print(GetLastError());
  }
//+------------------------------------------------------------------+