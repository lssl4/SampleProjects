
function [result] = myExpFunction(x)
    %Checks if the row vector have all real numbers and is a row vector
    if(isreal(x) && isrow(x))

      %Set n as zero for the taylor series
      n = 0;
   

      %Calculate the row vector initally when n = 0
      matrixresult = x.^n./My_Factorial(n);
      
      %Calculate the taylor series as long as n <=50 and the maximum of the
      %calculation is >=0.01 
      while n <=50 && max(abs(x.^n./My_Factorial(n))) >= 0.01 
          
        %Increment n  
        n = n+1;
        
        %Calculate the next taylor series again and add it to the
        %matrixresult
        matrixresult = matrixresult + x.^n./My_Factorial(n);
      
      end
      
     

      result = matrixresult;
    
    else
        %Else returns error
        error('Parameter is not a row vector and/or contains real numbers');
        
    end
        
end
