#property strict

#include <Trade\Trade.mqh>
#include <Trade\SymbolInfo.mqh>
#include <Trade\AccountInfo.mqh>
#include <Trade\PositionInfo.mqh>
#include <Libs\HistoryPositionInfo.mqh>

input long MagicNumber = 10000;
input String Strategy = 10000;

//+------------------------------------------------------------------+
//| Appllication Context with object defenitions.                    |
//+------------------------------------------------------------------+

CTrade *               Trade = NULL;
CJAVal *               JsonMapper = NULL;
CSymbolInfo *          SymbolInfo = NULL;
CAccountInfo *         AccountInfo = NULL;
CHistoryPositionInfo * HistoryInfo = NULL;
CPositionInfo *        PositionInfo = NULL;

//+------------------------------------------------------------------+
//| Context should destroy. Call in OnDeinit().                      |
//+------------------------------------------------------------------+
void DestroyContext()
  {
   delete Trade;
   delete JsonMapper;
   delete SymbolInfo;
   delete AccountInfo;
   delete HistoryInfo;
   delete PositionInfo;
  }

//+------------------------------------------------------------------+
//| Singleton of JsonMapper                                          |
//+------------------------------------------------------------------+
CJAVal *               JsonMapper()
  { return JsonMapper == NULL ? JsonMapper = new CJAVal(NULL, jtUNDEF) : JsonMapper; }
//+------------------------------------------------------------------+
//| Singleton of AccountInfo                                         |
//+------------------------------------------------------------------+
CAccountInfo *         AccountInfo()
  { return AccountInfo == NULL ? AccountInfo = new CAccountInfo() : AccountInfo; }
//+------------------------------------------------------------------+
//| Singleton of PositionInfo                                        |
//+------------------------------------------------------------------+
CPositionInfo *        PositionInfo()
  { return PositionInfo == NULL ? PositionInfo = new CPositionInfo() : PositionInfo; }
//+------------------------------------------------------------------+
//| Singleton of HistoryInfo                                         |
//+------------------------------------------------------------------+
CHistoryPositionInfo * HistoryInfo()
  { return HistoryInfo == NULL ? HistoryInfo = new CHistoryPositionInfo() : HistoryInfo; }
//+------------------------------------------------------------------+
//| Singleton of SymbolInfo                                          |
//+------------------------------------------------------------------+
CSymbolInfo *          SymbolInfo()
  {
   if(SymbolInfo != NULL)
      return SymbolInfo;

   SymbolInfo = new CSymbolInfo();
   SymbolInfo.Name(Symbol());
   return SymbolInfo;
  }
//+------------------------------------------------------------------+
//| Singleton of Trade                                               |
//+------------------------------------------------------------------+
CTrade * Trade()
  {
   if(Trade != NULL)
      return Trade;

   Trade = new CTrade();
   Trade.SetTypeFillingBySymbol(Symbol());
   Trade.SetExpertMagicNumber(MagicNumber);
   if(SymbolInfoInteger(SymbolInfo().Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_FOK)
      Trade.SetTypeFilling(ORDER_FILLING_FOK);
   else
      if(SymbolInfoInteger(SymbolInfo().Name(), SYMBOL_FILLING_MODE) == SYMBOL_FILLING_IOC)
         Trade.SetTypeFilling(ORDER_FILLING_IOC);
      else
         Trade.SetTypeFilling(ORDER_FILLING_RETURN);

   return Trade;
  }

string Strategy() { return Strategy; }
string Magic() { return MagicNumber; }