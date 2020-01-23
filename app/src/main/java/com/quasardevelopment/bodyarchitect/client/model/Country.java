package com.quasardevelopment.bodyarchitect.client.model;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 02.06.13
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public class Country implements Serializable
{
    static ArrayList<Country> countries;

    public int GeoId;

    public String EnglishName;

    public String NativeName;

    public static Collection<Country> getCountries()
    {
        if(countries==null)
        {
            countries=loadCountries();
        }
        return countries;
    }

    public static Country getCountry(final int geoId)
    {
        if(countries==null)
        {
            countries=loadCountries();
        }
        return Helper.FirstOrDefault(filter(new Predicate<Country>() {
            public boolean apply(Country item) {
                return item.GeoId == geoId;
            }
        }, countries));
    }

    private static ArrayList<Country> loadCountries() {

        ArrayList<Country> countries=new ArrayList<Country>();

        try {
            InputStream is = MyApplication.getAppContext().getResources().openRawResource(R.raw.countries);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("Country");
            for (int userNumber = 0; userNumber < items.getLength(); userNumber++) {

                Element countryElement = (Element) items.item(userNumber);
                String geoId=countryElement.getElementsByTagName("GeoId").item(0).getTextContent();
                String englishName=countryElement.getElementsByTagName("EnglishName").item(0).getTextContent();
                String nativeName=countryElement.getElementsByTagName("NativeName").item(0).getTextContent();
                Country country = new Country();
                country.NativeName=nativeName;
                country.EnglishName=englishName;
                country.GeoId=Integer.parseInt(geoId);
                countries.add(country);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return countries;
    }
}
