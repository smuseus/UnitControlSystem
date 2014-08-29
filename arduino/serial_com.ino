int value = 0;

static void handleInput (char c) {

  if ('0' <= c && c <= '9')
    value = 10 * value + c - '0'; //combines any number with multiple digits, i.e. multiple char
  else if (c == ',') {

  } 
  else if ('a' <= c && c <='z') {

    if(value <= 0) value = 0;
    // Serial.println(c);
    switch (c) {

/*    default:
       showHelp();
      break;
*/

    case 'b':
      if (value == 0){
        digitalWrite(AC_LOAD, LOW);
      } else {
        digitalWrite(AC_LOAD, HIGH);        
      }
      
      //dimming = value;     
    //  Serial.print("new dimValue = ");
    //  Serial.println(dimming);
      break;

    case 'u':
     /* 
      if ( value == 0) {
          _state = s[3].state;
          s[3].up();
          s[0].up();
          if (_state ==0) {
            s[3].down();
          }
      }
      else if (value == 3) {
          _state = s[0].state;
          s[0].up();
          s[3].up();
          if (_state ==0) {
            s[0].down();
          }
      } else {
        */
        s[value].up();  
      Serial.print(value);
      Serial.println("up");
      //shit this ended up ridicusly simple.. maybe some feedback needed for frontend?
      //}
      break;


    case 'd':
     /* 
      if ( value == 0) {
          _state = s[3].state; 
          s[3].down();
          s[0].down();
          if (_state == 1) {
            s[3].up();
          }
      }
      else if (value == 3) {
          
         _state = s[0].state;
          s[0].down();
          s[3].down();
          if (_state == 1) {
            s[0].up();
          }
          
      } else {
      */
      s[value].down();    
      Serial.print(value);
      Serial.println("down");
   // }
      break;

    }    

    value  = 0;
  } 
}

/*
void showHelp()
{
  Serial.println();
  Serial.println();
  Serial.println("XXXb - set blower to XXX value (0-100)");
  Serial.println("Xu - set solenoid X up (0-4)");
  Serial.println("Xd - set solenoid X down (0-4)");
  Serial.println();
}

*/










































