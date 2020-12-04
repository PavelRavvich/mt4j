#property strict

#include <Trade\Trade.mqh>
#include <Trade\SymbolInfo.mqh>
#include <Trade\AccountInfo.mqh>
#include <Trade\PositionInfo.mqh>
#include <Libs\HistoryPositionInfo.mqh>
#include <Provider\PositionProvider.mqh>
#include <Provider\MarketProvider.mqh>

input long MagicNumber = 10000000;
input String Strategy = "EXAMPLE";

//+------------------------------------------------------------------+
//| Appllication Context with object defenitions.                    |
//+------------------------------------------------------------------+

string Magic() { return MagicNumber; }
string Strategy() { return Strategy; }

CTrade *               Trade = NULL;
CJAVal *               JsonMapper = NULL;
CSymbolInfo *          SymbolInfo = NULL;
CAccountInfo *         AccountInfo = NULL;
CHistoryPositionInfo * HistoryInfo = NULL;
CPositionInfo *        PositionInfo = NULL;

CRestClient *          RestClient = NULL;
CApiService *          ApiService = NULL;
CInputsProvider *      InputsProvider = NULL;
CMarketProvider *      MarketProvider = NULL;
CRequestFactory *      RequestFactory = NULL;
CAccountProvider *     AccountProvider = NULL;
CPositionProvider *    PositionProvider = NULL;
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
   delete RestClient;
   delete ApiService;
   delete InputsProvider;
   delete MarketProvider;
   delete RequestFactory;
   delete AccountProvider;
   delete PositionProvider;
  }

//+------------------------------------------------------------------+
//| Singleton of CJAVal                                              |
//+------------------------------------------------------------------+
CJAVal *               JsonMapper()
  { return JsonMapper == NULL ? JsonMapper = new CJAVal(NULL, jtUNDEF) : JsonMapper; }
//+------------------------------------------------------------------+
//| Singleton of CAccountInfo                                        |
//+------------------------------------------------------------------+
CAccountInfo *         AccountInfo()
  { return AccountInfo == NULL ? AccountInfo = new CAccountInfo() : AccountInfo; }
//+------------------------------------------------------------------+
//| Singleton of CPositionInfo                                       |
//+------------------------------------------------------------------+
CPositionInfo *        PositionInfo()
  { return PositionInfo == NULL ? PositionInfo = new CPositionInfo() : PositionInfo; }
//+------------------------------------------------------------------+
//| Singleton of CHistoryPositionInfo                                |
//+------------------------------------------------------------------+
CHistoryPositionInfo * HistoryInfo()
  { return HistoryInfo == NULL ? HistoryInfo = new CHistoryPositionInfo() : HistoryInfo; }
//+------------------------------------------------------------------+
//| Singleton of CSymbolInfo                                         |
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
//| Singleton of CTrade                                              |
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
//+------------------------------------------------------------------+
//| Singleton of CPositionProvider                                   |
//+------------------------------------------------------------------+
CPositionProvider * PositionProvider() { return PositionProvider == NULL ? PositionProvider = new CPositionProvider() : PositionProvider; }
//+------------------------------------------------------------------+
//| Singleton of CMarketProvider                                     |
//+------------------------------------------------------------------+
CMarketProvider * MarketProvider() { return MarketProvider == NULL ? MarketProvider = new CMarketProvider() : MarketProvider; }
//+------------------------------------------------------------------+
//| Singleton of CInputsProvider                                     |
//+------------------------------------------------------------------+
CInputsProvider * InputsProvider() { return InputsProvider == NULL ? InputsProvider = new CInputsProvider() : InputsProvider; }
//+------------------------------------------------------------------+
//| Singleton of CAccountProvider                                    |
//+------------------------------------------------------------------+
CAccountProvider * AccountProvider() { return AccountProvider == NULL ? AccountProvider = new CAccountProvider() : AccountProvider; }
//+------------------------------------------------------------------+
//| Singleton of CRestClient                                         |
//+------------------------------------------------------------------+
CRestClient * RestClient() { return RestClient == NULL ? RestClient = new CRestClient() : RestClient; }
//+------------------------------------------------------------------+
//| Singleton of CRequestFactory                                     |
//+------------------------------------------------------------------+
CRequestFactory * RequestFactory() { return RequestFactory == NULL ? RequestFactory = new CRequestFactory() : RequestFactory; }
//+------------------------------------------------------------------+
//| Singleton of CApiService                                         |
//+------------------------------------------------------------------+
CApiService * ApiService() { return ApiService == NULL ? ApiService = new CApiService() : ApiService; }