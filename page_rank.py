f_node=open("result/index_src.txt",'r')
node_list={}
nodes=f_node.readlines()
N=len(nodes)
doc1=[0]*(N*2)
for node in nodes:  
	node=node.replace('\n','');
	node=node.replace('\\','/');
	word=node.split(' , ');
	node_list[word[1]]=int(word[0])
	doc1[int(word[0])]=word[1]
i=0
dic=[0]*N
table={}
for node in node_list:
	table[node_list[node]]=i
	dic[i]=node
	i+=1
f_graph=open("result/outlink.txt","r")
node_links=f_graph.readlines()
graph={}
for node_link in node_links:
	node_link=node_link.replace('\n','')
	node_link=node_link.split('[]');
	if not table.has_key(int(node_link[0])):
		continue;
	head=table[int(node_link[0])]
	node_link=node_link[1].split(" ")
	out_link=[]
	for i in node_link:
		if node_list.has_key(i) and not i=='':
			out_link.append(table[node_list[i]])
	if graph.has_key(head):
		graph[head]=graph[head]+out_link
	else:
		graph[head]=out_link
print "finish read file"
out=[0]*N
for i in node_list:
	m=table[node_list[i]]
	if(graph.has_key(m)):
		out[m]=len(graph[m])
	else:
		out[m]=0
TN=30
a=0.15
temp=1.0/N
PR=[temp]*N
temp=a/N
I=[temp]*N
print "finish initialization"
for i in range(0,TN):
	S=0
	for node_id in range(0,N):
		if(out[node_id]==0):
			S=S+(1-a)*PR[node_id]/N
		else:
			out_link=graph[node_id]
			temp=(1-a)*PR[node_id]/out[node_id]
			for n_id in out_link:
				I[n_id]=I[n_id]+temp
	print i
	for j in range(0,N):
		PR[j]=I[j]+S
		I[j]=a/N
	print i
result=[]
for j in range(0,N):
	result.append((j,PR[j]));
result=sorted(result,key=lambda t:t[1])
result.reverse()
print "finish calculate"
f_result=open("result.txt","w")
for i in result:
	f_result.write(str(node_list[dic[i[0]]])+" , "+str(i[1])+"\n")
f_result.close()