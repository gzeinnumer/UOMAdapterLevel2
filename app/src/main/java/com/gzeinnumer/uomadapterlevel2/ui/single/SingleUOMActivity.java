package com.gzeinnumer.uomadapterlevel2.ui.single;

import static com.gzeinnumer.uomadapterlevel2.helper.GblFunction.idr;
import static com.gzeinnumer.uomadapterlevel2.helper.GblFunction.s;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gzeinnumer.uomadapterlevel2.databinding.ActivitySingleUomBinding;
import com.gzeinnumer.uomadapterlevel2.databinding.ItemUomBinding;
import com.gzeinnumer.uomadapterlevel2.model.UOM;
import com.gzeinnumer.uomadapterlevel2.model.UOMConvert;
import com.gzeinnumer.uomadapterlevel2.model.UOMConvertResult;

import java.util.ArrayList;
import java.util.List;

public class SingleUOMActivity extends AppCompatActivity {

    private ActivitySingleUomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleUomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int priceCTN = 16000;
        int priceHGR = 8000;
        int pricePCS = 1000;

        list = new ArrayList<>();

//        list.add(new UOM(0, "CTN", priceCTN));
//        list.add(new UOM(1, "HGR", priceHGR));
//        list.add(new UOM(2, "PCS", pricePCS));
        list.add(new UOM(0, "CTN", priceCTN, 1));
        list.add(new UOM(1, "HGR", priceHGR, 1));
        list.add(new UOM(2, "PCS", pricePCS, 126));

        initAdapter();
        initConvertTools();
    }

    private List<UOM> list = new ArrayList<>();
    private UOMAdapter adapter;

    private void initAdapter() {

        adapter = new UOMAdapter(getApplicationContext(), list);

        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();

        adapter.setOnItemClickListener(isFocus -> {
            if (isFocus) countNow();
        });
        binding.progressCircular.setVisibility(View.VISIBLE);
        adapter.setOnFinishRender(() -> {
            binding.progressCircular.setVisibility(View.GONE);
            if (list.size() > 0) {
                initLastData(list);
            }
        });
    }

    private static final String TAG = "asdafasfasadsa";

    private void countNow() {
        String[] countAll = new String[adapter.getHolders().size()];
        for (int i = 0; i < adapter.getHolders().size(); i++) {
            ItemUomBinding bind = adapter.getHolders().get(i);
            if (bind!=null){
                String s = bind.tvQtyXPrice.getText().toString();
                if (s.length() > 0) {
                    countAll[i] = s;
                } else {
                    countAll[i] = "0";
                }
            }
        }

        double harga = 0;
        for (String value : countAll) {
            try {
                double current = Double.parseDouble(value);
                harga = Math.round(current + harga);
            } catch (Exception e) {
                Log.e("T_A_G", "countNow: "+e.getMessage());
            }
        }

        binding.tvPricePerProduct.setText(idr(s(harga)));
    }

    private void initLastData(List<UOM> list) {
        for (int i = 0; i < adapter.getHolders().size(); i++) {
            ItemUomBinding bind = adapter.getHolders().get(i);
            if (bind!=null){
                UOM current = list.get(i);
                bind.ed.setText(current.getLastData() + "");
                int harga = current.getLastData() * current.getPrice();
                bind.tvQtyXPrice.setText(harga + "");
            }
        }
        binding.progressCircular.setVisibility(View.GONE);
    }

    //convert Tools
    private void initConvertTools(){
        binding.btnConvert.setOnClickListener(view -> {
            list = new ArrayList<>();

            List<UOMConvert> toConvert = new ArrayList<>();
            for (int i = 0; i < adapter.getHolders().size(); i++) {
                ItemUomBinding bind = adapter.getHolders().get(i);
                if (bind!=null){
                    int buy = Integer.parseInt(bind.ed.getText().toString());
                    int inPcs = 0;
                    if (i==0){
                        inPcs = 100;
                    } else if (i==1){
                        inPcs = 25;
                    } else {
                        inPcs = 1;
                    }
                    String name = bind.tvUom.getText().toString();
                    toConvert.add(new UOMConvert(buy, inPcs, name));
                }
            }

            List<UOMConvertResult> res = uomConverter(toConvert);
            for (int i = 0; i < res.size(); i++) {
                Log.d(TAG, "onCreate: " + res.get(i).toString());
                int price;
                if (i==0){
                    price = 16000;
                } else if (i==1){
                    price = 8000;
                } else {
                    price = 1000;
                }
                list.add(new UOM(i, res.get(i).name, price, res.get(i).newValue));
            }
            initAdapter();
        });
    }

    private List<UOMConvertResult> uomConverter(List<UOMConvert> list) {
        int[] arrayPcs = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            UOMConvert d = list.get(i);
            int buy = d.buy;
            int inPcs = d.inPcs;
            arrayPcs[i] = buy * inPcs;
        }

        int allInPcs = 0;
        for (int d : arrayPcs) {
            allInPcs += d;
        }

        List<UOMConvertResult> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UOMConvert d = list.get(i);
            int total = allInPcs / d.inPcs;
            res.add(new UOMConvertResult(total + " " + d.name + " ", total));
            allInPcs = allInPcs - (total * d.inPcs);
        }

        return res;
    }
}