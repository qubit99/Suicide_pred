import scrapy


class UserPostsSpider(scrapy.Spider):
    name = "userposts"

    def start_requests(self):
        urls = [
            'https://www.takethislife.com/suicide/1/',
        ]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        # follow links to open ful posts
        for href in response.xpath(".//tbody[@id = 'threadbits_forum_21']/tr/td/div/a"):
            yield response.follow(href, self.parse_post)

        # follow pagination links 
        for href in response.xpath("//td[@class='alt1']/a[@rel = 'next']"):
            yield response.follow(href, self.parse)

    def parse_post(self, response): 
        def getHeading(response):
            heading = response.xpath(
                ".//div[@class='smallfont']/strong[1]").extract_first()
            
            return heading[40:-39]
            
        yield{
            'heading': getHeading(response),
            'post': response.xpath("//div[@class='nolinks']").extract_first()
        }
