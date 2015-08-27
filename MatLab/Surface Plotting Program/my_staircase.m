function [ z ] = my_staircase( a,b,h,n )
%My representation of an elliptical staircase that changes in size
%with height can be modelled by the parametric equations

if isnumeric(a) && isnumeric(b) && isnumeric(h) && isnumeric(n)

t = [0:0.1:2*pi*n];



r = ((a*b)./((b.*cos(t)).^2+(a.*sin(t)).^2).^(0.5)).*exp(-0.04.*t);


x = r.*cos(t);
y = r.*sin(t);
z = (h.*t)./(2*pi*n);


plot3(x,y,z);
grid('on');
xlabel('X(metres)');
ylabel('Y(metres)');
zlabel('Z(metres)');
title('My Elliptical Spiraling Staircase');
else
    error('The function inputs must be numberic');
end
end

