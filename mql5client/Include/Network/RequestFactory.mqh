#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Provider\MarketProvider.mqh>
#include <Provider\AccountProvider.mqh>
#include <Provider\PositionProvider.mqh>

//+------------------------------------------------------------------+
//| Factory http request's body                                      |
//+------------------------------------------------------------------+
class CRequestFactory
  {
   MarketProvider *    market_provider;
   AccountProvider *   account_provider;
   PositionProvider *  position_provider;
public:
                     CRequestFactory()
     {
      market_provider = MarketProvider();
      account_provider = AccountProvider();
      position_provider = PositionProvider();
     }
                    ~CRequestFactory() {}

public:
   string            GetAddAdvisorRequestBody(string inputs);
   string            GetSignalRequestBody(string advisor_id, string strategy_name);
  };

//+------------------------------------------------------------------+
//| Build requests body for register advisor                         |
//+------------------------------------------------------------------+
string::CRequestFactory            GetAddAdvisorRequestBody(string inputs)
  {
   return "{ \"magic\": " + (string) Magic() + ", \"inputs\": " + inputs + " }";
  }

//+------------------------------------------------------------------+
//| Build request body for fetch signal from remote resource         |
//+------------------------------------------------------------------+
string::CRequestFactory            GetSignalRequestBody(string advisor_id, string strategy_name)
  {
   return "{ \"advisorId\": \"" +  advisor_id + "\", \"strategyName\": \"" + strategy_name +
          "\", \"rates\": " + market_provider.GetRates() +
          ", \"positions\": " + position_provider.GetPositions() +
          ", \"account\": " + account_provider.GetAccount() +
          " }";
  }
//+------------------------------------------------------------------+
