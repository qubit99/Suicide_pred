import scrapy


class UserPostsSpider(scrapy.Spider):
    name = "userposts"

    def start_requests(self):
        urls = [
            'https://www.enotalone.com/forum/forumdisplay.php?f=48&page=2',
        ]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        # follow links to get full posts
        for href in response.xpath(".//h3[@class = 'threadtitle']/a[@class = 'title']"):
            yield response.follow(href, self.parse_post)

        # follow pagination links
        for href in response.xpath(".//div[@class='threadpagenav']/form/span/a[@rel = 'next']"):
            yield response.follow(href, self.parse)

    def parse_post(self, response):
        def getHeading(response):
            heading = response.xpath(
                ".//div[@class='postrow']/h2/text()").extract_first()

            return heading

        yield{
            'heading': getHeading(response),
            'post': response.xpath(".//div/blockquote/text()").extract_first()
        }
