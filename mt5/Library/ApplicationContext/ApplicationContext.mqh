#property strict

// Meta Trader classes.
#include <Trade\Trade.mqh>
#include <Trade\SymbolInfo.mqh>
#include <Trade\AccountInfo.mqh>
#include <Trade\PositionInfo.mqh>

// Custom utils
#include <Library\Common\Utils.mqh>
#include <Library\Common\Enums.mqh>
#include <Library\Common\Structures.mqh>

// Helpers
#include <Library\Helper\JAson.mqh>
#include <Library\Helper\HistoryPositionInfo.mqh>

// Providers
#include <Library\Provider\PositionProvider.mqh>
#include <Library\Provider\AccountProvider.mqh>
#include <Library\Provider\MarketProvider.mqh>
#include <Library\Provider\InputsProvider.mqh>

// Network
#include <Library\Network\RequestFactory.mqh>
#include <Library\Network\RestClient.mqh>
#include <Library\Network\ApiService.mqh>

// Excecutor
#include <Library\Transaction\SignalExecutor.mqh>


input long MagicNumber    = 10000000;
input string StrategyName = "EXAMPLE";
input int TransactionRetryPolicy = 10;
input int RatesBufferSize = 20;


//+------------------------------------------------------------------+
//| Appllication Context with object defenitions.                    |
//+------------------------------------------------------------------+

long Magic() { return MagicNumber; }
string Strategy() { return StrategyName; }
int RatesBufferSize() { return RatesBufferSize; }
int RetryPolicy() { return TransactionRetryPolicy; }

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
CSignalExecutor *      SignalExecutor = NULL;
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
   delete SignalExecutor;
   delete AccountProvider;
   delete PositionProvider;
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
//| Singleton of CJAVal                                              |
//+------------------------------------------------------------------+
CJAVal *               JsonMapper()
  { return JsonMapper == NULL ? JsonMapper = new CJAVal(NULL, jtUNDEF) : JsonMapper; }
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
//| Singleton of CAccountInfo                                        |
//+------------------------------------------------------------------+
CAccountInfo *         AccountInfo()
  { return AccountInfo == NULL ? AccountInfo = new CAccountInfo() : AccountInfo; }
//+------------------------------------------------------------------+
//| Singleton of CHistoryPositionInfo                                |
//+------------------------------------------------------------------+
CHistoryPositionInfo * HistoryInfo()
  { return HistoryInfo == NULL ? HistoryInfo = new CHistoryPositionInfo() : HistoryInfo; }
//+------------------------------------------------------------------+
//| Singleton of CPositionInfo                                       |
//+------------------------------------------------------------------+
CPositionInfo *        PositionInfo()
  { return PositionInfo == NULL ? PositionInfo = new CPositionInfo() : PositionInfo; }
//+------------------------------------------------------------------+
//| Singleton of CRestClient                                         |
//+------------------------------------------------------------------+
CRestClient * RestClient()
  { return RestClient == NULL ? RestClient = new CRestClient() : RestClient; }
//+------------------------------------------------------------------+
//| Singleton of CApiService                                         |
//+------------------------------------------------------------------+
CApiService * ApiService()
  { return ApiService == NULL ? ApiService = new CApiService() : ApiService; }
//+------------------------------------------------------------------+
//| Singleton of CInputsProvider                                     |
//+------------------------------------------------------------------+
CInputsProvider * InputsProvider()
  { return InputsProvider == NULL ? InputsProvider = new CInputsProvider() : InputsProvider; }
//+------------------------------------------------------------------+
//| Singleton of CMarketProvider                                     |
//+------------------------------------------------------------------+
CMarketProvider * MarketProvider()
  { return MarketProvider == NULL ? MarketProvider = new CMarketProvider() : MarketProvider; }
//+------------------------------------------------------------------+
//| Singleton of CRequestFactory                                     |
//+------------------------------------------------------------------+
CRequestFactory * RequestFactory()
  { return RequestFactory == NULL ? RequestFactory = new CRequestFactory() : RequestFactory; }
//+------------------------------------------------------------------+
//| Singleton of CSignalExecutor                                     |
//+------------------------------------------------------------------+
CSignalExecutor * SignalExecutor()
  { return SignalExecutor == NULL ? SignalExecutor = new CSignalExecutor() : SignalExecutor; }
//+------------------------------------------------------------------+
//| Singleton of CAccountProvider                                    |
//+------------------------------------------------------------------+
CAccountProvider * AccountProvider()
  { return AccountProvider == NULL ? AccountProvider = new CAccountProvider() : AccountProvider; }
//+------------------------------------------------------------------+
//| Singleton of CPositionProvider                                   |
//+------------------------------------------------------------------+
CPositionProvider * PositionProvider()
  { return PositionProvider == NULL ? PositionProvider = new CPositionProvider() : PositionProvider; }