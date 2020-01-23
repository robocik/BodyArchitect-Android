package com.quasardevelopment.bodyarchitect.client.util;

import android.text.TextUtils;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 23.05.13
 * Time: 08:53
 * To change this template use File | Settings | File Templates.
 */
public class TrainingBuilder {
    public void PrepareCopiedStrengthTraining(StrengthTrainingItemDTO dto, Settings.CopyStrengthTrainingMode mode)
    {
        if (Settings.getCopyStrengthTrainingMode().equals(Settings.CopyStrengthTrainingMode.OnlyExercises))
        {
            dto.series.clear();
        }
        else if (Settings.getCopyStrengthTrainingMode().equals(Settings.CopyStrengthTrainingMode.WithoutSetsData))
        {
            for (SerieDTO serieDto : dto.series)
            {
                serieDto.repetitionNumber = null;
                serieDto.weight = null;
            }
        }
    }

    public void PrepareCopiedStrengthTraining(StrengthTrainingEntryDTO entry, Settings.CopyStrengthTrainingMode mode)
    {
        for (StrengthTrainingItemDTO dto : entry.entries)
        {
            PrepareCopiedStrengthTraining(dto, mode);
        }

    }


    public void SetPreviewSets(StrengthTrainingItemDTO origItem, StrengthTrainingItemDTO item)
    {
        for (int i = 0; i < origItem.series.size(); i++)
        {
            item.series.get(i).tag = origItem.series.get(i);
        }
    }
    public void SetPreviewSets(StrengthTrainingEntryDTO origEntry, StrengthTrainingEntryDTO entry)
    {
        List<SerieDTO> origSets=getAllSets(origEntry);
        List<SerieDTO> copiedSets =getAllSets(entry);
        for (int i = 0; i < origSets.size(); i++)
        {
            copiedSets.get(i).tag = origSets.get(i);
        }
    }

    List<SerieDTO> getAllSets(StrengthTrainingEntryDTO entry)
    {
        ArrayList<SerieDTO> sets = new ArrayList<SerieDTO>();
        for (StrengthTrainingItemDTO item :entry.entries)
        {
            sets.addAll(item.series);
        }
        return sets;
    }

    public void CleanSingleSupersets(StrengthTrainingEntryDTO entry)
    {
        List<StrengthTrainingItemDTO> entries= filter(new Predicate<StrengthTrainingItemDTO>() {
            public boolean apply(StrengthTrainingItemDTO item) {
                return !TextUtils.isEmpty(item.superSetGroup);
            }
        }, entry.entries);

        for (final StrengthTrainingItemDTO item : entries)
        {
            List<StrengthTrainingItemDTO> sameGroup= filter(new Predicate<StrengthTrainingItemDTO>() {
                public boolean apply(StrengthTrainingItemDTO x) {
                    return !TextUtils.equals(x.superSetGroup,item.superSetGroup);
                }
            }, entry.entries);
            if (sameGroup.size() < 2)
            {
                item.superSetGroup = null;
            }
        }
    }

    public boolean FillRepetitionNumber;

    public void FillTrainingFromPlan(TrainingPlanDay planDay, StrengthTrainingEntryDTO strengthEntry)
    {
        strengthEntry.entries.clear();
        for (TrainingPlanEntry trainingPlanEntry : planDay.entries)
        {
            StrengthTrainingItemDTO strengthItem = new StrengthTrainingItemDTO();
            strengthItem.exercise = trainingPlanEntry.exercise;
            strengthItem.doneWay = trainingPlanEntry.doneWay;
            strengthItem.superSetGroup = trainingPlanEntry.groupName;
            strengthItem.trainingPlanItemId = trainingPlanEntry.globalId;
            strengthEntry.entries.add(strengthItem);
            strengthItem.strengthTrainingEntry=strengthEntry;

            for (TrainingPlanSerie set : trainingPlanEntry.sets)
            {
                SerieDTO serie = new SerieDTO();
                if(strengthItem.exercise.exerciseType.equals(WS_Enums.ExerciseType.Cardio))
                {
                    if (FillRepetitionNumber &&  set.repetitionNumberMin!=null)
                    {
                        serie.weight = Double.valueOf(set.repetitionNumberMin);
                    }
                }
                else
                {
                    if (FillRepetitionNumber && set.repetitionNumberMax!=null && set.repetitionNumberMin!=null && set.repetitionNumberMax .equals(set.repetitionNumberMin))
                    {
                        serie.repetitionNumber = Double.valueOf(set.repetitionNumberMax);
                    }
                }


                serie.isSuperSlow = set.isSuperSlow;
                serie.isRestPause = set.isRestPause;
                serie.dropSet = set.dropSet;
                serie.setType =  set.repetitionsType;
                serie.trainingPlanItemId = set.globalId;
                strengthItem.series.add(serie);
                serie.strengthTrainingItem=strengthItem;
            }
        }
        strengthEntry.trainingPlanItemId = planDay.globalId;
        strengthEntry.trainingPlanId = planDay.trainingPlan.globalId;
    }
}
