
function [result] = My_Factorial (n)
  %Sets the result to 1
  result = 1;
  
  %Keep multiplying to result as long as n >1
  while n >1
    result = result*n;
    n = n-1;


  end
  
  
