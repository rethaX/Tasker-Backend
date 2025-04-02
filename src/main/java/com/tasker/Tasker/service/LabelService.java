package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Label;
import com.tasker.Tasker.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label createLabel(String name, String color) {
        Label label = new Label();
        label.setName(name);
        label.setColor(color);
        return labelRepository.save(label);
    }

    public Label getLabel(Long labelId) {
        return labelRepository.findById(labelId)
                .orElseThrow(() -> new RuntimeException("Label not found with id: " + labelId));
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    public Label updateLabel(Long labelId, String name, String color) {
        Label label = getLabel(labelId);
        label.setName(name);
        label.setColor(color);
        return labelRepository.save(label);
    }

    public void deleteLabel(Long labelId) {
        labelRepository.deleteById(labelId);
    }
}