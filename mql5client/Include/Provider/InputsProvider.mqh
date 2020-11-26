#property strict

/*
   InputsProvider usage example.

   InputsProvider * ip = new InputsProvider();
   ip.InputString("input_name_1", "input_value_1");
   ip.InputDouble("input_name_2", 1.1234);
   ip.InputInteger("input_name_3", 12345);
   ip.InputLong("input_name_4", 12345678);
   datetime now = TimeCurrent();
   ip.InputDatetime("input_name_5", now);
   ip.InputTimeSeconds("input_name_6", (int) now);
   ip.InputTimeMilliseconds("input_name_7", now * 1000);
   string jsonBuild = ip.Build(); // InputsProvider instance stay imutable.
   string jsonGet = ip.GetJsonInputs(); // Can be used only after .Build()
*/

//+------------------------------------------------------------------+
//| Build JSON array from inputs.                                    |
//+------------------------------------------------------------------+
class InputsProvider
  {
   string            inputs;
   bool              is_builed;
   string            _empty_inputs;
   string            _input_string_formatter;
   string            _input_double_formatter;
   string            _input_long_formatter;
   string            _keys[];
public:
                     InputsProvider()
     {
      inputs = "";
      is_builed = false;
      _empty_inputs = "[]";
      _input_long_formatter = "{ \"key\": \"%s\", \"value\": %.0f, \"type\": \"NUMBER\" }";
      _input_string_formatter = "{ \"key\": \"%s\", \"value\": \"%s\", \"type\": \"STRING\" }";
      _input_double_formatter = "{ \"key\": \"%s\", \"value\": %.5f, \"type\": \"NUMBER\" }";
     }
                    ~InputsProvider() {}

public:
   string             Build();
   string             GetJsonInputs();
   void              InputString(string key, string value);
   void              InputDouble(string key, double value);
   void              InputLong(string key, long value);
   void              InputInteger(string key, int value);
   void              InputDatetime(string key, datetime date);
   void              InputTimeSeconds(string key, int seconds);
   void              InputTimeMilliseconds(string key, long milliseconds);
   string            ToString();
private:
   void              AddKey(string key);
   bool              ContainKey(string key);
  };

//+------------------------------------------------------------------+
//| Build inputs as JSON array. Can be called only ones.             |
//+------------------------------------------------------------------+
string::InputsProvider             Build()
  {
   if(inputs == "")
     {
      Alert("Warning! Inputs is empty.");
      return _empty_inputs;
     }

   if(is_builed)
      Alert("Error! Method Build() can be called only ones.");

   is_builed = true;
   return inputs = "[" + StringSubstr(inputs, 0, StringLen(inputs) - 2) + "]";
  }

//+------------------------------------------------------------------+
//| Get inputs as JSON array. Reqired call Build() before.           |
//+------------------------------------------------------------------+
string::InputsProvider             GetJsonInputs()
  {
   if(!is_builed)
     {
      Alert("Build() wasn't called. Method Build() should be called before GetInputs().");
      return _empty_inputs;
     }
   return inputs;
  }

//+------------------------------------------------------------------+
//| Add string input                                                 |
//+------------------------------------------------------------------+
void::InputsProvider              InputString(string key, string value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(_input_string_formatter, key, value) + ", ";
  }

//+------------------------------------------------------------------+
//| Add double input                                                 |
//+------------------------------------------------------------------+
void::InputsProvider              InputDouble(string key, double value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(_input_double_formatter, key, value) + ", ";
  }

//+------------------------------------------------------------------+
//| Add long input                                                   |
//+------------------------------------------------------------------+
void::InputsProvider              InputLong(string key, long value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(_input_long_formatter, key, value) + ", ";
  }

//+------------------------------------------------------------------+
//| Add int input                                                                 |
//+------------------------------------------------------------------+
void::InputsProvider              InputInteger(string key, int value)
  {
   InputLong(key, (long) value);
  }

//+------------------------------------------------------------------+
//| Add time datetime input                                          |
//+------------------------------------------------------------------+
void::InputsProvider              InputDatetime(string key, datetime date)
  {
   InputLong(key, (long) date * 1000);
  }

//+------------------------------------------------------------------+
//| Add time seconds input                                           |
//+------------------------------------------------------------------+
void::InputsProvider              InputTimeSeconds(string key, int seconds)
  {
   InputLong(key, (long) seconds * 1000);
  }

//+------------------------------------------------------------------+
//| Add time milliseconds input                                      |
//+------------------------------------------------------------------+
void::InputsProvider              InputTimeMilliseconds(string key, long milliseconds)
  {
   InputLong(key, milliseconds);
  }

//+------------------------------------------------------------------+
//| return inputs as string (not valid JSON just for logging and debug)|
//+------------------------------------------------------------------+
string::InputsProvider            ToString()
  {
   return inputs;
  }

//+------------------------------------------------------------------+
//| Add key                                                          |
//+------------------------------------------------------------------+
void::InputsProvider              AddKey(string key)
  {
   int size = ArraySize(_keys);
   ArrayResize(_keys, size + 1);
   _keys[size] = key;
  }

//+------------------------------------------------------------------+
//| Check kay already exist                                          |
//+------------------------------------------------------------------+
bool::InputsProvider              ContainKey(string key)
  {
   for(int i = 0; i < ArraySize(_keys); i++)
      if(_keys[i] == key)
         return true;

   return false;
  }
//+------------------------------------------------------------------+