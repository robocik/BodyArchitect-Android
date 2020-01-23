package com.quasardevelopment.bodyarchitect.client.util;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.*;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 27.05.13
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class SerieValidator
{
    private HashMap<WS_Enums.ExerciseType,MyEntry<Double,Double>> limits;

    public SerieValidator()
    {
        
        limits = new HashMap<WS_Enums.ExerciseType, MyEntry<Double, Double>>();
        limits.put(WS_Enums.ExerciseType.Barki, new MyEntry<Double, Double>(30.0, 400.0));
        limits.put(WS_Enums.ExerciseType.Biceps, new MyEntry<Double, Double>(20.0, 200.0));
        limits.put(WS_Enums.ExerciseType.Brzuch, new MyEntry<Double, Double>(30.0, 200.0));
        limits.put(WS_Enums.ExerciseType.Czworoboczny, new MyEntry<Double, Double>(40.0, 500.0));
        limits.put(WS_Enums.ExerciseType.Klatka, new MyEntry<Double, Double>(50.0, 600.0));
        limits.put(WS_Enums.ExerciseType.Lydki, new MyEntry<Double, Double>(50.0, 600.0));
        limits.put(WS_Enums.ExerciseType.Nogi, new MyEntry<Double, Double>(50.0, 600.0));
        limits.put(WS_Enums.ExerciseType.Plecy, new MyEntry<Double, Double>(50.0, 600.0));
        limits.put(WS_Enums.ExerciseType.Przedramie, new MyEntry<Double, Double>(20.0, 200.0));
        limits.put(WS_Enums.ExerciseType.Triceps, new MyEntry<Double, Double>(20.0, 300.0));
    }

    public static List<SerieDTO> GetIncorrectSets(StrengthTrainingEntryDTO entry)
    {
        List<SerieDTO> incorrectSets = new ArrayList<SerieDTO>();
        if(entry==null)
        {//we delete this entry
            return incorrectSets;
        }
        SerieValidator validator = new SerieValidator();

        for (StrengthTrainingItemDTO item : entry.entries)
        {
            incorrectSets.addAll(validator.GetIncorrectSets(item));
        }
        return incorrectSets;
    }

    public List<SerieDTO> GetIncorrectSets(StrengthTrainingItemDTO item)
    {
        final List<SerieDTO> incorrectSets = new ArrayList<SerieDTO>();
        if (!limits.containsKey(item.exercise.exerciseType))
        {
            return incorrectSets;
        }

        List<SerieDTO> allWithWeight= filter(new Predicate<SerieDTO>() {
            public boolean apply(SerieDTO x) {
                return x.weight != null;
            }
        }, item.series);

        final MyEntry<Double, Double> limit = limits.get(item.exercise.exerciseType);
        List<SerieDTO> wrongBecauseOfMaxLimit= filter(new Predicate<SerieDTO>() {
            public boolean apply(SerieDTO x) {
                return  x.weight >= limit.getValue();
            }
        }, allWithWeight);

        incorrectSets.addAll(wrongBecauseOfMaxLimit);

        List<SerieDTO> afterLimit= filter(new Predicate<SerieDTO>() {
            public boolean apply(SerieDTO x) {
                return  !incorrectSets.contains(x);
            }
        }, allWithWeight);

        if (afterLimit.size() < 3)
        {
            return incorrectSets;
        }


        Collections.sort(afterLimit,new Comparator<SerieDTO>() {
            @Override
            public int compare(SerieDTO serieDTO, SerieDTO serieDTO2) {
                return serieDTO.weight.compareTo(serieDTO2.weight);
            }
        });

        List<Double> withoutMax = new ArrayList<Double>();
        for (SerieDTO set: afterLimit)
        {
            withoutMax.add(set.weight);
        }
        withoutMax.remove(withoutMax.size()-1);//remove last item (max weight)


        double max = withoutMax.get(withoutMax.size()-1);//last item
        withoutMax.add(max + limit.getKey());

        double srWithoutMax = srednia(withoutMax);
        double odchSdWithoutMax = odchylenie(srWithoutMax, withoutMax);
        for (SerieDTO set : afterLimit)
        {
            if (3 * odchSdWithoutMax < Math.abs(set.weight - srWithoutMax))
            {
                incorrectSets.add(set);
            }
        }
        return incorrectSets;
    }


    double srednia(List<Double> liczby)
    {
        double suma = 0;
        for (double d : liczby)
        {
            suma += d;
        }
        return suma / liczby.size();
    }

    double odchylenie(double srednia, List<Double> tab)
    {
        double dodaj = 0;
        double odchylenie = 0;
        for (int i = 0; i < tab.size(); i++)
        {
            double val=tab.get(i);
            dodaj = dodaj + (val - srednia) * (val - srednia);
        }


        odchylenie = Math.sqrt(dodaj / tab.size());
        return odchylenie;
    }
}
