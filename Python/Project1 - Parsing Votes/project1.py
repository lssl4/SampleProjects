"""Name: Shaun Leong (20753794)"""


import os.path

#Question 1
def getCandidates(f):
    if os.path.exists(f):
        file=open(f,"r")
        textLine = file.read()
        splitText = textLine.split("\n")
        if "" in splitText:
            splitText.remove("")
        return splitText
    else:
        emptyList = []
        return emptyList, "File is not found"
    
    
#Question 2
def parseVote(s):
    vote = 0
    if s.strip().isdigit():
        vote = int(s)
    elif s.strip() == "":
        vote = 0
    else:
        vote = -1
    return vote

#Question 3
def parsePaper(s,n):
    numberList = s.split(",")
    message = ""
    parseVoteList = []
    countOfZeroes = 0
    if len(numberList) > n: #if the lenght of list is greater than number of candidates. 
        message = "too long"
    else:
        for item in numberList:
            parseVoteList.append(parseVote(item))
        for element in parseVoteList:
            if element == -1:
                parseVoteList = []
                message = "non-digits"
            if element == 0:
                countOfZeroes = countOfZeroes +1
                if countOfZeroes == len(parseVoteList):
                    parseVoteList = []
                    message = "blank"
    return parseVoteList, message

#Question 4
def getPapers(f,n):
    ballotsList = []
    if os.path.exists(f):
        file=open(f,"r")
        for eachLine in file:
            eachBallot = parsePaper(eachLine, n)
            ballotsList.append(eachBallot)
        return ballotsList
    else:
        return [], "You need to download file", "smallfile.txt"

#Question 5
def normalisePaper(p,n): #sum(p) > 0
    totalSum = 0
    while len(p) < n:
        p.append(0.0)
    for number in p:
        totalSum = totalSum + number
    index = 0
    while index < len(p):
        p[index] = p[index]/totalSum
        index += 1
    return p

#Question 6
def normalisePapers(ps,n):
    for eachList in ps:
        normalisePaper(eachList, n)
    return ps

#Question 7
def countVotes(cs, ps): # ps have been normalised for an election with len(cs) candidates
    index = 0
    while index < len(ps):
        normalisedTotal = list((x+y) for x,y in zip(ps[0], ps[index]))
        index += 1
    count = 0
    combinedList = []
    while count < len(normalisedTotal):
        coupleList = []
        coupleList.append(normalisedTotal[count])
        coupleList.append(cs[count])
        combinedList.append(coupleList)
        count += 1
    return combinedList
                              
""" while index < len(eachList):
    normalisedTotal = (sum(x) for x in zip(final, ballotpaer)
    normalisedTotal.append(eachList[index])
    index += 1"""

    
    
    

#Question 8

def printCount(c):
    for eachList in c:
        print("%6.2f %-15s" % (eachList[0], eachList[1]))

#Question 9
def main():
    candidatesList = getCandidates(input("What is the name of the candidates list? "))
    print (candidatesList)
    
    ballotFileName = input("What is the name of the text file with the ballot papers? ")
    numOfCandidates = int(input("How many candidates are there? "))
    
    ballotPapersOutput = getPapers(ballotFileName, numOfCandidates)
    print (ballotPapersOutput)

    combinedScores = []
    for eachTuple in ballotPapersOutput:
        normScoreList = eachTuple[0]
        if normScoreList != []:
            combinedScores.append(normScoreList)
    print(combinedScores)
    
    normPapersOutput = normalisePapers(combinedScores, numOfCandidates)
    print(normPapersOutput)

    countVotesOutput = countVotes(candidatesList, normPapersOutput)
    print(countVotesOutput)

    printCountOutput = printCount(countVotesOutput)
    
    numOfInformalVotes = 0
    numOfFormalVotes = 0

    #Final results output text
    print("Nerdvanian election 2014" + "\n")
    print ("There were", str(numOfInformalVotes), "informal votes")
    print("There were", str(numOfFormalVotes), "formal votes" + "\n"*2, printCountOutput)
    
    
    
    

    

    
    

    
    
        
   
        

    



            
        
    

        
        
        
    
        
        
    
    



