package de.uniks.pmws2021.view;

import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AlternateListCellFactory implements Callback<ListView<Hero>, ListCell<Hero>> {

    @Override
    public ListCell<Hero> call(ListView<Hero> param) {
        return new HeroListCell();
    }

    private static class HeroListCell extends ListCell<Hero> {


        @Override
        protected void updateItem(Hero hero, boolean empty) {
            ImageView avatar = new ImageView();
            HBox cell = new HBox();
            VBox textCell = new VBox();
            VBox statsCell = new VBox();
            Label name = new Label();
            Label mode = new Label();
            Label atk = new Label();
            Label def = new Label();
            Label coins = new Label();

            super.updateItem(hero, empty);
            if (empty) {
                setItem(null);
                setGraphic(null);
            } else if ((hero != null) && (!empty)) {
                cell.setAlignment(Pos.CENTER);
                cell.setSpacing(40);
                textCell.setAlignment(Pos.CENTER);
                statsCell.setAlignment(Pos.CENTER_LEFT);

                avatar.setImage(ResourceManager.loadIcon("Hero" + hero.getIconNr()));
                avatar.setFitWidth(80);
                avatar.setFitHeight(80);

                name.setText(hero.getName());
                name.setStyle("-fx-font-weight: bold");
                mode.setText(hero.getMode());
                textCell.getChildren().addAll(name, mode);

                atk.setText("Attack: Level " + hero.getStats().get(0).getLevel());
                def.setText("Defence: Level " + hero.getStats().get(1).getLevel());
                coins.setText("Coins: " + hero.getCoins());
                statsCell.getChildren().addAll(atk, def, coins);

                cell.setStyle("-fx-background-color: #2C2F33;");
                cell.getChildren().addAll(avatar, textCell, statsCell);

                cell.setPrefHeight(avatar.getFitHeight());
                setPrefHeight(avatar.getFitHeight());
                setGraphic(cell);
            }
        }
    }
}
