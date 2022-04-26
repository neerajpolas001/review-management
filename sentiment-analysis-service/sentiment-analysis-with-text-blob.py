from flask import Flask, jsonify, request
from textblob import TextBlob

app = Flask(__name__)


@app.route('/sentiment-analysis/advance', methods=['POST'])
def basic_sentiment_analysis():
    text = request.json.get("text")
    blob = TextBlob(text);
    if blob.polarity > 0.5:
        return jsonify({"sentiment": "positive",
                        "polarity": blob.polarity
                        })
    elif blob.polarity == 0.5:
        return jsonify({"sentiment": "neutral",
                        "polarity": blob.polarity
                        })
    else:
        return jsonify({"sentiment": "negative",
                        "polarity": blob.polarity
                        })


if __name__ == '__main__':
    app.run(host="localhost", port=6000, debug=True)
