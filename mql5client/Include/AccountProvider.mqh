#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Trade\AccountInfo.mqh>


class AccountProvider
  {

   CAccountInfo      _account_info;

   string            _account_formatter;

public:
                     AccountProvider()
     {_account_formatter = "{ \"id\": %.0f, \"balance\": %.2f, \"freeMargin\": %.2f, \"margin\": %.2f, \"owner\": \"%s\", \"company\": \"%s\" }"; }
                    ~AccountProvider() {}


public:
   string            GetAccount()
     {
      long login = _account_info.Login();
      double balance =_account_info.Balance();
      double freeMargin = _account_info.FreeMargin();
      double margin = _account_info.Margin();
      string name = _account_info.Name();
      string company = _account_info.Company();
      return StringFormat(_account_formatter, login, balance, freeMargin, margin, name, company);
     }
  };