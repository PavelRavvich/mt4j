#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Provider\MarketProvider.mqh>
#include <Provider\AccountProvider.mqh>
#include <Provider\PositionProvider.mqh>

//+------------------------------------------------------------------+
//| Factory http request's body                                      |
//+------------------------------------------------------------------+
class RequestFactory
  {
   MarketProvider *    market_provider;
   AccountProvider *   account_provider;
   PositionProvider *  position_provider;
public:
                     RequestFactory()
     {
      market_provider = new MarketProvider();
      account_provider = new AccountProvider();
      position_provider = new PositionProvider();
     }

                    ~RequestFactory() { delete market_provider; delete position_provider; delete account_provider; }

public:
   string            GetAddAdvisorRequestBody(string inputs);
   string            GetSignalRequestBody(string advisor_id, string strategy_name);
  };

//+------------------------------------------------------------------+
//| Build requests body for register advisor                         |
//+------------------------------------------------------------------+
string::RequestFactory            GetAddAdvisorRequestBody(string inputs)
  {
   return "{ \"magic\": " + (string) Magic() + ", \"inputs\": " + inputs + " }";
  }

//+------------------------------------------------------------------+
//| Build request body for fetch signal from remote resource         |
//+------------------------------------------------------------------+
string::RequestFactory            GetSignalRequestBody(string advisor_id, string strategy_name)
  {
   return "{ \"advisorId\": \"" +  advisor_id + "\", \"strategyName\": \"" + strategy_name +
          "\", \"rates\": " + market_provider.GetRates() +
          ", \"positions\": " + position_provider.GetPositions() +
          ", \"account\": " + account_provider.GetAccount() +
          " }";
  }
//+------------------------------------------------------------------+
