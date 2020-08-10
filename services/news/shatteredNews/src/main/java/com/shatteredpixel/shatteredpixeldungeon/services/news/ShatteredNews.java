/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2020 Trashbox Bobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.services.news;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.XmlReader;
import com.watabou.noosa.Game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShatteredNews extends NewsService {

	@Override
	public void checkForArticles(boolean useMetered, boolean preferHTTPS, NewsResultCallback callback) {

		if (!useMetered && !Game.platform.connectedToUnmeteredNetwork()){
			callback.onConnectionFailed();
			return;
		}

		Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
		if (preferHTTPS) {
			httpGet.setUrl("https://shatteredpixel.com/feed");
		} else {
			httpGet.setUrl("http://shatteredpixel.com/feed");
		}

		Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
			@Override
			public void handleHttpResponse(Net.HttpResponse httpResponse) {
				ArrayList<NewsArticle> articles = new ArrayList<>();
				XmlReader reader = new XmlReader();
				XmlReader.Element xmlDoc = reader.parse(httpResponse.getResultAsStream());

				SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

				for (XmlReader.Element xmlArticle : xmlDoc.getChildrenByName("entry")){
					NewsArticle article = new NewsArticle();
					article.title = xmlArticle.get("title");
					try {
						article.date = dateParser.parse(xmlArticle.get("published"));
					} catch (ParseException e) {
						Game.reportException(e);
					}
					article.summary = xmlArticle.get("summary");
					article.URL = xmlArticle.getChildByName("link").getAttribute("href");
					if (!preferHTTPS) {
						article.URL = article.URL.replace("https://", "http://");
					}

					try {
						article.icon = xmlArticle.getChildByName("category").getAttribute("term");
					} catch (Exception e){
						article.icon = null;
					}

					articles.add(article);
				}
				callback.onArticlesFound(articles);
			}

			@Override
			public void failed(Throwable t) {
				callback.onConnectionFailed();
			}

			@Override
			public void cancelled() {
				callback.onConnectionFailed();
			}
		});
	}

}