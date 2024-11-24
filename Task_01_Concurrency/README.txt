I created this project in Intellij. Something interesting i found out, was even though i tried to use stream
distinct and sort it, it would print out multiple hamming numbers like for example 1,1,1,2,2,3,3,3 and so on. 
And the sort didnt worked also, because the order would get shuffled. But we got all the HammingNumbers. 

Then i tried it with TreeSet, because i thought that my implementation was wrong. But that didn't solve the problem. So i 
just sort and distinct again by using the another TreeSet in the Copy Class, so i would get my printed HammingNumbers in order and
don't allow duplicates, and that worked for me.

And in the CustomBlockingQueue i didn't used the wait, notifyAll, notify functions. I used a ReentrantLock with a condition isEmpty to check
if the Queue is empty or not. I hope that this would also be sufficient for completing the task. 