f1=open("result/index_src.txt",'r')
f2=open("result.txt",'r')
f3=open("index.txt",'w')
nodes1=f1.readlines()
nodes2=f2.readlines()
node_list={}
for node in nodes2:  
	node=node.replace('\n','');
	word=node.split(' , ');
	node_list[int(word[0])]=word[1]
N=len(nodes1)
print N
for i in range(0,N):
	node1=nodes1[i].replace('\n','');
	word1=node1.split(' , ');
	index=word1[0]
	url=word1[1]
	title=word1[2]
	PR=node_list[int(word1[0])]
	f3.write(index+' , '+url+' , '+title+' , '+PR+'\n')