#property strict

#include <ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Provide account info as JSON                                     |
//+------------------------------------------------------------------+
class AccountProvider
  {
   CAccountInfo *    account_info;
   string            account_formatter;
public:
                     AccountProvider()
     {
      account_info = AccountInfo();
      account_formatter = "{ \"id\": %.0f, \"balance\": %.2f, \"freeMargin\": %.2f, \"margin\": %.2f, \"owner\": \"%s\", \"company\": \"%s\" }";
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
   string name = account_info.Name();
   long login = account_info.Login();
   double margin = account_info.Margin();
   string company = account_info.Company();
   double balance = account_info.Balance();
   double freeMargin = account_info.FreeMargin();
   return StringFormat(account_formatter, login, balance, freeMargin, margin, name, company);
  }
//+------------------------------------------------------------------+
