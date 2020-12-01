#property strict

#include <Trade\Trade.mqh>
#include <Trade\SymbolInfo.mqh>
#include <Trade\PositionInfo.mqh>
#include <Libs\HistoryPositionInfo.mqh>

CTrade *               Trade = NULL;
CSymbolInfo *          SymbolInfo = NULL;
CPositionInfo *        PositionInfo = NULL;
CHistoryPositionInfo * HistoryInfo = NULL;

CSymbolInfo * SymbolInfo() { return SymbolInfo == NULL ? SymbolInfo = new CSymbolInfo() : SymbolInfo; }
CPositionInfo * PositionInfo() { return PositionInfo == NULL ? PositionInfo = new CPositionInfo() : PositionInfo; }
CHistoryPositionInfo * HistoryInfo() { return HistoryInfo == NULL ? HistoryInfo = new CHistoryPositionInfo() : HistoryInfo; }
CTrade * Trade()
  {
   if(Trade != NULL)
      return Trade;

   Trade = new CTrade();
   Trade.SetExpertMagicNumber(magic);
   Trade.SetTypeFillingBySymbol(symbol);
   if(SymbolInfoInteger(SymbolInfo().Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_FOK)
      Trade.SetTypeFilling(ORDER_FILLING_FOK);
   else
      if(SymbolInfoInteger(SymbolInfo().Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_IOC)
         Trade.SetTypeFilling(ORDER_FILLING_IOC);
      else
         Trade.SetTypeFilling(ORDER_FILLING_RETURN);

   return Trade;
  }