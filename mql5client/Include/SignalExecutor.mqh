#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Trade\Trade.mqh>
#include <Enums.mqh>
#include <Structures.mqh>


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

   void              Execute(Signal &signal)
     {
      if(signal.type == NO_ACTION)
         return;
      if(signal.type == BUY)
         Buy(signal);
      if(signal.type == SELL)
         Sell(signal);
      if(signal.type == CLOSE)
         Close(signal);
      if(signal.type == UPDATE)
         Update(signal);
     }

private:

   void              Buy(Signal &signal)
     {
      // todo
     }

   void              Sell(Signal &signal)
     {
      // todo
     }

   void              Close(Signal &signal)
     {
      // todo
     }

   void              Update(Signal &signal)
     {
      // todo
     }

   double            StopLoss(int stopLoss, SignalType type)
     {
      return (type == BUY ? Ask() - stopLoss * _symbolInfo.Point() : Bid() + stopLoss * _symbolInfo.Point());
     }

   double            TakeProfit(int takeProfit, SignalType type)
     {
      return (type == BUY ? Ask() + takeProfit * _symbolInfo.Point() : Bid() - takeProfit * _symbolInfo.Point());
     }

   double            Ask()
     {
      return NormalizeDouble(SymbolInfoDouble(_symbolInfo.Name(), SYMBOL_ASK), _symbolInfo.Digits());
     }

   double            Bid()
     {
      return NormalizeDouble(SymbolInfoDouble(_symbolInfo.Name(), SYMBOL_BID), _symbolInfo.Digits());
     }

  };