

%Part A
TriangleArea = struct('Width', {},'Height', {},'Area',{});


%Part B
FID = fopen('sample.txt', 'r');

allNumbers = fscanf(FID, '%f');

%initialize the number of triangles to numberOfTri
numberOfTri = allNumbers(1);
fclose(FID);



%remove number of triangles
allNumbers = allNumbers(2:end);

%reshaping allNumbers
allNumbers = reshape(allNumbers, 2, numberOfTri);

%transpose allNumbers
allNumbers = allNumbers';

for ii = 1: numberOfTri
    rowOfTriangle = allNumbers(ii,:);
    
    TriangleArea(ii).Width = rowOfTriangle(1);
    TriangleArea(ii).Height = rowOfTriangle(2);
    TriangleArea(ii).Area = rowOfTriangle(1)*rowOfTriangle(2)*0.5;
        
   
end



%Part C
FID2 = fopen('sample2.txt','w');
fprintf(FID2, 'Number of Triangles are %d\n\n', numberOfTri);

for ii = 1: numberOfTri
    fprintf(FID2, 'Width of Triangle %d is: %.6f\n', ii, TriangleArea(ii).Width);
    fprintf(FID2, 'Height of Triange %d is: %.6f\n', ii, TriangleArea(ii).Height);
    fprintf(FID2, 'Area of Triangle %d is: %.6f\n\n', ii, TriangleArea(ii).Area);
    

end

%Part D
maxArea = TriangleArea(1).Area;
minArea = TriangleArea(1).Area;
maxTriangleSerial = 1;
minTriangleSerial = 1;

for ii = 1:numberOfTri
  
    if TriangleArea(ii).Area > maxArea
        maxTriangleSerial = ii;
        maxArea = TriangleArea(ii).Area;
    elseif TriangleArea(ii).Area < minArea
        minArea = TriangleArea(ii).Area;
        minTriangleSerial =ii;
    end
end


fprintf(FID2, 'Maximum and Minimum Areas of Triangles\n');
fprintf(FID2, 'Triangle %d has minimum area of %.6f\n',minTriangleSerial,minArea);
fprintf(FID2, 'Triangle %d has maximum area of %.6f\n',maxTriangleSerial,maxArea);

fclose(FID2);