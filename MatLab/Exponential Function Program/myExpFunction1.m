
function [result] = myExpFunction1(x)
       

    %Checks if the row vector have all real numbers and is a row vector
    if(isreal(x) && isrow(x))
        
        %Set n as zero for the taylor series
        n = 0;
   

      %Calculate the row vector initally when n = 0
      matrixresult = x.^n./My_Factorial(n);
      
      %Calculate the taylor series as long as n <=50 and the maximum of the
      %calculation is >=0.01
      while n <=50 && max(abs(x.^n./My_Factorial(n))) >= 0.01 
        
        
        %Plot the matrixresult
         
        figure;
  
         
  plot(x, exp(x),x,matrixresult ); 
  legend( 'MatLab Exp Function', sprintf('myExpFunction for n = %d', n));
  title('Demonstrating Taylor Series of My Exponential Function and The MatLab Exponential Function');
  
  %calculate next series
  n = n+1;
    matrixresult = matrixresult + x.^n./My_Factorial(n);
      
      end
      
     

      result = matrixresult;
    
    else
        %Else returns error
        error('Parameter is not a row vector and/or contains real numbers');
        
    end

  
       
   
    
end