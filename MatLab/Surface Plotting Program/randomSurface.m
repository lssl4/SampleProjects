
%Part A
Z0 = rand(5,5);

%Part B
Y0 = [1:1:5];
X0 = [1:1:5];

[X0,Y0] = meshgrid(X0,Y0);
figure('Name','Figure 2a','NumberTitle', 'off');
surf(X0,Y0,Z0);
xlabel('X (Units)');
ylabel('Y (Units)');
zlabel('Z (Units)');
title('Random Surface Generation Plot');
grid('on');

%Part C

X1 = [1:0.1:5];
Y1 = [1:0.1:5];

[X1,Y1] = meshgrid(X1,Y1);


%Part D
Z1= interp2(X0,Y0,Z0,X1,Y1,'cubic');

%Part E
figure('Name','Figure 2b','NumberTitle', 'off');
surf(X1,Y1,Z1);
grid('on');

colormap 'hsv';
shading 'interp';

%Part F
hold('on');
contour(X1,Y1,Z1);


%Part G
colorbar;
caxis([0 1]);

%Part H
grid('on');
xlabel('X (Units)');
ylabel('Y (Units)');
zlabel('Z (Units)');
title('Interpolation of Random Surface Generation Plot');




