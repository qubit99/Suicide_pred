import praw
import time
import csv
import json

search_queries = {'kill me', 'suicide', 'suicidal', 'my friend is suicidal', 'feel like dying',
                  'depressed', 'killing myself', 'self harm', 'ready to end my life', 'raped', 'live anymore', 'my child is having suicidal intent', 'son is suicidal',
                  'daughter is suicidal', 'ignores my problems', 'fucking die', 'lost my job', 'no reason to live', 'cut my body', 'kill myself', 'cheated on me', 'i cry', 'life sucks'}


if __name__ == "__main__":

    reddit = praw.Reddit(client_id='', client_secret="",
                         password='', user_agent='personal script by /u/qubit',
                         username='architranjan9')

    print(reddit.user.me())

    data = {}
    data['posts'] = []

    for string in search_queries:
        time.sleep(1)
        for post in reddit.subreddit('all').search(string, sort="relevance"):
            title = post.title
            postbody = post.selftext
            data['posts'].append({
                'post': title+postbody,
                'intent': ''
            })
            print(title)

    with open('redditdata.txt', 'w', newline='') as file:
        json.dump(data, file)
