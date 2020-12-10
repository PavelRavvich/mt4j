#property strict

//+------------------------------------------------------------------+
//| Build JSON array from inputs.                                    |
//+------------------------------------------------------------------+
class CInputsProvider
  {
private:
   string            inputs;
   bool              is_builed;
   string            empty_inputs;
   string            input_long_formatter;
   string            input_string_formatter;
   string            input_double_formatter;
   string            input_boolean_formatter;
   string            keys[];
public:
                     CInputsProvider(void);
                    ~CInputsProvider(void) {}
public:
   string            Build(void);
   string            GetJsonInputs(void);
   void              InputBoolean(string key, bool value);
   void              InputString(string key, string value);
   void              InputDouble(string key, double value);
   void              InputLong(string key, long value);
   void              InputInteger(string key, int value);
   void              InputDatetime(string key, datetime date);
   void              InputTimeSeconds(string key, int seconds);
   void              InputTimeMilliseconds(string key, long milliseconds);
   string            ToString(void);
private:
   void              AddKey(string key);
   bool              ContainKey(string key);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CInputsProvider::CInputsProvider(void)
  {
   inputs = "";
   is_builed = false;
   empty_inputs = "[]";
   input_long_formatter = "{ \"key\": \"%s\", \"value\": %.0f, \"type\": \"NUMBER\" }";
   input_double_formatter = "{ \"key\": \"%s\", \"value\": %.5f, \"type\": \"NUMBER\" }";
   input_boolean_formatter = "{ \"key\": \"%s\", \"value\": %s, \"type\": \"BOOLEAN\" }";
   input_string_formatter = "{ \"key\": \"%s\", \"value\": \"%s\", \"type\": \"STRING\" }";
  }
//+------------------------------------------------------------------+
//| Build inputs as JSON array. Can be called only ones.             |
//+------------------------------------------------------------------+
string CInputsProvider::Build(void)
  {
   if(inputs == "")
     {
      Alert("Warning! Inputs is empty.");
      return empty_inputs;
     }

   if(is_builed)
      Alert("Error! Method Build() can be called only ones.");

   is_builed = true;
   return inputs = "[" + StringSubstr(inputs, 0, StringLen(inputs) - 2) + "]";
  }
//+------------------------------------------------------------------+
//| Get inputs as JSON array. Reqired call Build() before.           |
//+------------------------------------------------------------------+
string CInputsProvider::GetJsonInputs(void)
  {
   if(!is_builed)
     {
      Alert("Build() wasn't called. Method Build() should be called before GetInputs().");
      return empty_inputs;
     }
   return inputs;
  }
//+------------------------------------------------------------------+
//| Add boolean input                                                |
//+------------------------------------------------------------------+
void CInputsProvider::InputBoolean(string key, bool value)
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
   inputs = inputs + StringFormat(input_boolean_formatter, key, value ? "true" : "false") + ", ";
  }
//+------------------------------------------------------------------+
//| Add string input                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputString(string key, string value)
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
   inputs = inputs + StringFormat(input_string_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add double input                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputDouble(string key, double value)
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
   inputs = inputs + StringFormat(input_double_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add long input                                                   |
//+------------------------------------------------------------------+
void CInputsProvider::InputLong(string key, long value)
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
   inputs = inputs + StringFormat(input_long_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add int input                                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputInteger(string key, int value)
  {
   InputLong(key, (long) value);
  }
//+------------------------------------------------------------------+
//| Add time datetime input                                          |
//+------------------------------------------------------------------+
void CInputsProvider::InputDatetime(string key, datetime date)
  {
   InputLong(key, (long) date * 1000);
  }
//+------------------------------------------------------------------+
//| Add time seconds input                                           |
//+------------------------------------------------------------------+
void CInputsProvider::InputTimeSeconds(string key, int seconds)
  {
   InputLong(key, (long) seconds * 1000);
  }
//+------------------------------------------------------------------+
//| Add time milliseconds input                                      |
//+------------------------------------------------------------------+
void CInputsProvider::InputTimeMilliseconds(string key, long milliseconds)
  {
   InputLong(key, milliseconds);
  }
//+------------------------------------------------------------------+
//| return inputs as string (not valid JSON just for logging and debug)|
//+------------------------------------------------------------------+
string CInputsProvider::ToString(void)
  {
   return inputs;
  }
//+------------------------------------------------------------------+
//| Add key                                                          |
//+------------------------------------------------------------------+
void CInputsProvider::AddKey(string key)
  {
   int size = ArraySize(keys);
   ArrayResize(keys, size + 1);
   keys[size] = key;
  }
//+------------------------------------------------------------------+
//| Check kay already exist                                          |
//+------------------------------------------------------------------+
bool CInputsProvider::ContainKey(string key)
  {
   for(int i = 0; i < ArraySize(keys); i++)
      if(keys[i] == key)
         return true;

   return false;
  }
//+------------------------------------------------------------------+