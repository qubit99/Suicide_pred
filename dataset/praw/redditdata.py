import praw
import time
import csv

search_queries = {'kill me', 'suicide', 'suicidal', 'my friend is suicidal', 'feel like dying',
                  'depressed', 'killing myself', 'self harm', 'ready to end my life', 'raped', 'live anymore', 'my child is having suicidal intent', 'son is suicidal',
                  'daughter is suicidal', 'ignores my problems', 'fucking die', 'lost my job', 'no reason to live', 'cut my body', 'kill myself', 'cheated on me'}





if __name__ == "__main__":


    reddit = praw.Reddit(client_id='jazU2BeYT1YamQ', client_secret="tMctKBrH3DunCFkqpOR15uVP5IM",
                        password='anshul2007', user_agent='personal script by /u/qubit',
                        username='architranjan9')

    print(reddit.user.me())

    posts = [["head","post"]]

    for string in search_queries:
        time.sleep(1)
        for post in reddit.subreddit('all').search(string,sort = "relevance"):
            title = post.title.encode('utf-8')
            post = post.selftext.encode('utf-8')
            posts.append([title,post])
    
    with open('redditdata.csv', 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerows(posts)
            
