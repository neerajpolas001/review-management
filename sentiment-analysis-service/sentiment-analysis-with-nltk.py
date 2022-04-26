from flask import Flask, jsonify, request
import nltk
from nltk.sentiment.vader import SentimentIntensityAnalyzer
import string

nltk.download("vader_lexicon")

app = Flask(__name__)


@app.route('/sentiment-analysis/basic', methods=['POST'])
def basic_sentiment_analysis():
    text = request.json.get("text")
    text = text.lower()
    cleaned_text = text.translate(str.maketrans('', '', string.punctuation))
    sid = SentimentIntensityAnalyzer()
    score = sid.polarity_scores(cleaned_text)
    sentiment = ""
    poarity = 0.0
    if score['neg'] > score['pos']:
        sentiment = "Negative"
        polarity = score['neg']
    elif score['neg'] < score['pos']:
        sentiment = "Positive"
        polarity = score['pos']
    else:
        sentiment = "Neutral"
        polarity = score['neu']

    return jsonify({"sentiment": sentiment,
                    "polarity": score['compound']
                    })


if __name__ == '__main__':
    app.run(host="localhost", port=5000, debug=True)
