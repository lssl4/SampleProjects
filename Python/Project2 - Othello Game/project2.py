#Siong Leong (20753794) & Gregory Edmonds (21487148)

from turtle import Turtle
from itertools import *
t = Turtle()
t.ht()
import random


#Question 1
def initialiseBoard(n):
    if n>0 and n%2==0:
        board = [[0 for count in range(n)] for count in range(n)]
        #for the latter middle row
        board[n//2][n//2] = 1
        board[n//2][n//2-1] = -1
        #for the former middle row
        board[n//2-1][n//2] = -1
        board[n//2-1][n//2-1] = 1
        return board
    else:
        print ("Error. Put in an even n")

#Question 2
def drawBoard(b):
    length = 500
    rows = len(b)
    columns = len(b[0])
    
    t.speed(0)
    t.up()
    
    #draw the dark brown border
    t.goto(length/2+length*.05, length/2+length*.05)
    t.setheading(270)
    t.down()
    t.fillcolor("#603311")
    t.begin_fill()
    for line in range(4):
        t.forward(length*1.1)
        t.right(90)
    t.end_fill()
    t.up()
    
    #draw the green board


    
    t.goto(length/2, length/2)
    t.setheading(270)
    t.down()
    t.fillcolor("darkGreen")
    t.begin_fill()
    for line in range(4):
        t.forward(length)
        t.right(90)
    t.end_fill()
    t.up()
    
    #draw the row and column lines
    
    t.fillcolor("black")
    t.right(90)
    t.forward(length/rows)
    t.setheading(270)
    t.down()

    for line in range(1,rows):
        t.goto((length/2)-line*(length/rows), length/2)
        t.down()
        t.forward(length)
        t.up()

    t.setheading(180)

    for line in range(1,columns):
        t.goto(length/2, (length/2)-line*(length/columns))
        t.down()
        t.forward(length)
        t.up()
    #puts in original pieces    
    lengthOfSquare = length/columns
    for eachRow in range(rows):
        for eachColumn in range(columns):
            if b[eachRow][eachColumn] > 0:
                t.goto(-length/2 + lengthOfSquare/2 + lengthOfSquare*eachColumn, -length/2 + lengthOfSquare/2 + lengthOfSquare*eachRow)
                t.dot(lengthOfSquare*.75, "black")
            elif b[eachRow][eachColumn] < 0:
                t.goto(-length/2 + lengthOfSquare/2 + lengthOfSquare*eachColumn, -length/2 + lengthOfSquare/2 + lengthOfSquare*eachRow)
                t.dot(lengthOfSquare*.75, "white")






def updatePieces(b, r, c):
    length = 500
    rows = len(b)
    columns = len(b[0])
    t.speed(8)
    t.up()
    
    lengthOfSquare = length/columns
    
    if b[r][c] > 0:
        t.goto(-length/2 + lengthOfSquare/2 + lengthOfSquare*c, -length/2 + lengthOfSquare/2 + lengthOfSquare*r)
        t.dot(lengthOfSquare*.75, "black")
    elif b[r][c] < 0:
        t.goto(-length/2 + lengthOfSquare/2 + lengthOfSquare*c, -length/2 + lengthOfSquare/2 + lengthOfSquare*r)
        t.dot(lengthOfSquare*.75, "white")
        

def scoreBoardGraphics():
    length = 200

    #draw outer rectangle
    t.speed(0)
    t.up()
    t.goto(300, 0)
    t.setheading(0)
    t.down()
    t.fillcolor("gray")
    t.begin_fill()
    
    for line in range(2):
        t.forward(length)
        t.right(90)
        t.forward(length//2)
        t.right(90)

    t.end_fill()
    t.up()

    #draw pieces
    t.goto(300+0.25*length, -25)
    t.dot(length*0.15, "black")
    t.setheading(0)
    t.forward(100)
    t.dot(length*0.15, "white")
    
    
    
    
        

#Question 3: m is (r,c,ms), b is board, p is player (1,-1)
def move(b, m, p):
    if m != ():
        
    
        r = m[0]
        c = m[1]
        b[r][c] = 1*p
        updatePieces(b,r,c)
        
        directions = m[2]
        (u,v) = (0,0)
        
        
        for directTuple in directions:
            u = directTuple[0]
            v = directTuple[1]

            capRow = r + u
            capCol = c + v
            
            while capRow < (len(b)) and capCol < (len(b[0])) and capRow >= 0 and capCol >= 0:
                
                if b[capRow][capCol]*p >= 0:
                    break
                elif b[capRow][capCol]*p < 0:
                    
                    b[capRow][capCol] = abs(b[capRow][capCol]) + 1
                    if p == -1:
                        b[capRow][capCol] = b[capRow][capCol] * (-1)
                
                updatePieces(b, capRow, capCol)
                capRow = capRow + u
                capCol = capCol + v
    
    return b


    


#Question 4 (legal direction)
def legalDirection(r,c,b,p,u,v):
    capRow = r + u
    capCol = c + v
    captured = 0
    if capRow >= 0 and capCol >= 0:
        while capRow < (len(b)) and capCol < (len(b[0])) and capRow >= 0 and capCol >= 0:
            if b[capRow][capCol]*p > 0:
                if captured > 0:
                    return True
                else:
                    return False
            elif b[capRow][capCol]*p < 0:
                captured = captured + 1
            else:
                return False
            capRow = capRow + u
            capCol = capCol + v
    else:
        return False
        


#Question 5
def legalMove(r, c, b, p):
    legalDirectionList = []
    for eachDirection in list(product([1,0,-1], repeat =2)):
        u = eachDirection[0]
        v = eachDirection[1]
        if (u,v) != (0,0):
            if legalDirection(r,c,b,p,u,v) == True:
                legalDirectionList.append((u,v))
    return legalDirectionList



#Question 6
def moves(b, p):
    legalMoveList = []
    for eachRow in range(len(b)):
        for eachColumn in range(len(b[0])):
            if b[eachRow][eachColumn] == 0:
                if legalMove(eachRow,eachColumn,b,p) != []:
                    legalMoveList.append((eachRow,eachColumn,legalMove(eachRow,eachColumn,b,p)))
    return legalMoveList


#Question 7
def selectMoves(ms, b, p):
    if ms != []:                  
        return random.choice(ms)
    else:
        return ()
    
 

    
        


#Question 8
def scoreBoard(b):
    blackPlayer = 0
    whitePlayer = 0
    for eachRow in range(len(b)):
        for eachColumn in range(len(b[0])):
            if b[eachRow][eachColumn] > 0:
                blackPlayer = blackPlayer + b[eachRow][eachColumn]
            elif b[eachRow][eachColumn] < 0:
                whitePlayer = whitePlayer + abs(b[eachRow][eachColumn])
    return (blackPlayer, whitePlayer)



#Question 9
def main():
    initialBoard = initialiseBoard(int(input("What board size?: ")))
    drawBoard(initialBoard)

    b= initialBoard 
    p = 1
    
    while moves(b,1) != [] or moves(b,-1)!=[]:
        b = move(b, selectMoves(moves(b,p), b, p), p)
        p = p *-1
    
    print(scoreBoard(b))
    if scoreBoard(b)[0] > scoreBoard(b)[1]:
        print("Black Player Wins!")
    elif scoreBoard(b)[0] == scoreBoard(b)[1]:
        print("Tie-Game!")
    else:
        print("White Player Wins!")

def mainHuman():
    initialBoard = initialiseBoard(int(input("What board size? (How many squares in each row and column): ")))
    drawBoard(initialBoard)

    b= initialBoard
    p = 1

    
    while moves(b,1) != [] or moves(b,-1)!=[]:
        if p==1:
            b = move(b, selectMoves(moves(b,p), b, p), p)
            p = p *-1
        else:
            print("\nThese are your moves: \n")
            for i in range(len(moves(b,-1))):
                print (moves(b,-1)[i],"Index Number: ", i)
            myMove = int(input("Choose a move by index number: "))
            #if myMove < len(moves(b,-1)) and myMove >= 0:
            b = move(b, moves(b,-1)[myMove], p)
            p = p * -1
    
    print(scoreBoard(b))

if __name__ == "__main__":
    main()    

