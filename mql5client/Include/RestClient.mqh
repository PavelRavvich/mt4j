#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <RequestFactory.mqh>
#include <Structures.mqh>


class RestClient
  {

   long              _magic;

   string            _url_formatter;

   // Example: RestConfig restConfig = {"http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };
   RestConfig        _restConfig;

   RequestFactory *  _requestFactory;

public:
                     RestClient(long magic, RestConfig &restConfig)
     {
      _magic = magic;
      _restConfig = restConfig;
      _url_formatter = "%s:%.0f%s";
      _requestFactory = new RequestFactory(magic);
     }
                    ~RestClient() { delete _requestFactory; }

public:

   string               Connect(string inputs)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/advisor/add");
      request.body = _requestFactory.GetAddAdvisorRequestBody(inputs);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Post(request, response);
      if(response.status != 200)
         Alert(StringFormat("Connection to Advisor: %.0f failed", _magic)); ExpertRemove();
      else
         Alert(StringFormat("Connection to Advisor: %.0f success", _magic));
      return response.body;
     }

   string               GetSignal(string advisor_id, string strategy_name, string symbol)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/signal");
      request.body = _requestFactory.GetSignalRequestBody(advisor_id, strategy_name, symbol);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Get(request, response);
      return response.body;
     }

private:

   void                   Post(HttpRequest &request, HttpResponse &response)
     {
      char responseBody[];
      uchar requestBody[];
      string responseHeaders;
      StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
      int status = WebRequest("POST", request.url, request.headers, _restConfig.timeout, requestBody, responseBody, responseHeaders);
      response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
      response.status = status;
     }

   void                   Get(HttpRequest &request, HttpResponse &response)
     {
      char responseBody[];
      uchar requestBody[];
      string responseHeaders;
      StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
      int status = WebRequest("POST", request.url, request.headers, _restConfig.timeout, requestBody, responseBody, responseHeaders);
      response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
      response.status = status;
     }

  };