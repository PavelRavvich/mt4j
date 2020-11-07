#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <RequestFactory.mqh>

struct HttpResponse
  {
   int               status;
   string            body;
  };

struct HttpRequest
  {
   string            url;
   string            body;
   string            headers;
  };

struct RestConfig
  {
   int               port = 80;
   int               timeout = 3000;
   string            host = "http://127.0.0.1";
   string            headers = "Content-Type: application/json\r\n";
  };


class RestClient
  {

   string            _url_formatter;

   RestConfig     *  _restConfig;

   RequestFactory *  _requestFactory;

public:
                     RestClient(long magic, RestConfig &restConfig)
     {
      _restConfig = restConfig;
      _url_formatter = "%s:%.0f%s";
      _requestFactory = new RequestFactory(magic);
     }
                    ~RestClient()
     {
      delete _requestFactory;
      delete _restConfig;
     }

public:

   string               Connect(string inputs)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/advisor");
      request.body = _requestFactory.GetAddAdvisorRequestBody(inputs);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Post(request, response);
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

   string               AddPosition(Position &position)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/position/add");
      request.body = _requestFactory.GetPositionRequestBody(position);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Post(request, response);
      return response.body;
     }

   string               UpdatePosition(Position &position)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/position/update");
      request.body = _requestFactory.GetPositionRequestBody(position);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Post(request, response);
      return response.body;
     }

   string               HistoryPosition(Position &position)
     {
      HttpRequest request;
      request.url = StringFormat(_url_formatter, _restConfig.host, _restConfig.port, "/api/position/hisory");
      request.body = _requestFactory.GetPositionRequestBody(position);
      request.headers = _restConfig.headers;
      HttpResponse response;
      Post(request, response);
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