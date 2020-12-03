#property strict

#include <ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Provide account info as JSON                                     |
//+------------------------------------------------------------------+
class AccountProvider
  {
   CAccountInfo *    account_info;
   string            _account_formatter;
public:
                     AccountProvider()
     {
      account_info = AccountInfo();
      _account_formatter = "{ \"id\": %.0f, \"balance\": %.2f, \"freeMargin\": %.2f, \"margin\": %.2f, \"owner\": \"%s\", \"company\": \"%s\" }";
     }
                    ~AccountProvider() {}
public:
   string            GetAccount();
  };
//+------------------------------------------------------------------+
//| Build account info as JSON                                       |
//+------------------------------------------------------------------+
string::AccountProvider            GetAccount()
  {
   long login = account_info.Login();
   double balance = account_info.Balance();
   double freeMargin = account_info.FreeMargin();
   double margin = account_info.Margin();
   string name = account_info.Name();
   string company = account_info.Company();
   return StringFormat(_account_formatter, login, balance, freeMargin, margin, name, company);
  }
//+------------------------------------------------------------------+
