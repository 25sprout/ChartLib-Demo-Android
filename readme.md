#MPAndroid 目錄
[MPAndroidChart](#MPAndroid)
- [基本介紹](#基本介紹)
- [圖表類型 與 Layout 設定](#圖表類型與Layout設定)
- [Data設定與操作](#Data設定與操作)
  - [新增/移除Data](#新增/移除Data)
      - [DataSet](#ModifyDataSet)
      - [ChartData](#ModifyChartData)
  - [DataStyle](#DataStyle)
    - [DataPoint](#DataPoint)
    - [Line](#DataLine)
    - [Highlight](#Highlight)
    - [Fill](#Fill)
    - [ValueFormatter](#ValueFormatter)
- [Axis軸線設定](#Axis軸線設定)
    - [XAxis](#XAxis)
        - [Position](#XAxisPosition)
        - [Label](#XAxisLabel)
        - [Line](#XAxisLine)
    - [YAxis](#YAxis)
        - [Max/MinValue](#YAxisMaxMinValue)
        - [Order](#YAxisOrder)
        - [Space](#YAxisSpace)
        - [Position](#YAxisPosition)
        - [Count](#YAxisCount)
        - [Line](#YAxisLine)
- [LimitLine](#LimitLine)
- [Legend圖例設定](#Legend圖例設定)
  - [取得Legend圖例區與設定位置](#取得Legend圖例區與設定位置)
  - [圖例組屬性設定](#圖例組屬性設定)
  - [圖例邊距](#圖例邊距)
  - [圖例顏色組設置](#圖例顏色組設置)
- [MakerView設置](#MakerView設置)
- [ViewPort調整](#ViewPort調整)
  - [ShowRange](#ShowRange)
  - [Moving](#Moving)
  - [ZoomIn/Out](#ZoomIn/Out)
  - [Offset](#Offset)
- [BasicStyle](#BasicStyle)
- [Animation](#Animation)

---
<br>
<div id="MPAndroid"></div>
# MPAndroidChart

MPAndroid 是一個 Open Source 的 Library，主要用於繪製各式圖表，先有 Android 版本後才開發 iOS 版本，兩版本的 API 設計方式都是同樣的，且是以 Android 為準。
##### Library 官方網址
* [Android Gtihub Repo](https://github.com/PhilJay/MPAndroidChart)
* [iOS Github Repo](https://github.com/PhilJay/MPAndroidChart)

## Gradle 設定
```java
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.PhilJay:MPAndroidChart:v2.1.5'
}
```
其他加入 Library 的方法可以參考[這裡](https://github.com/PhilJay/MPAndroidChart#usage)。



---
<br><br>
<div id="基本介紹"></div>
##基本介紹

MPAndroid 架構如下，主要分為 LineData、Axis、Legend、MakerView、ViewPort：
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447038568088.png)

本篇文章將會依序介紹這五個類別的用法，我們先來看一個基本圖表會如下圖：

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.chart-intro.png)

<span style="color:#FFC107;"> ■ </span>黃色區塊：YAXis，負責 Y 軸的樣式，如：刻度間距、 顯示刻度數量、背景橫線、顯示橫向 LimitLine
<span style="color:#4CAF50;"> ■ </span>綠色區塊：XAxis，負責 X 軸的樣式，如：刻度間距、顯示刻度數量、背景直線、顯示直向 LimitLine
<span style="color:#673AB7;"> ■ </span>紫色區塊：Legend，負責圖例的樣式，如：圖例形狀、行距、間距、自定義顏色
<span style="color:#212121;"> ■ </span>黑色箭頭：LineData，圖表資料與一些樣式設定，如：資料集合、線條樣式、資料點樣式
<span style="color:#FF9500;"> ■ </span>橘色圓形：MakerView，點選資料點會跳出來的泡泡框

其中最重要的就是 LineData，如果圖表沒有 LineData 資料就只單純顯示「`No chart data available`」的文字
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.nodata.PNG)

所以選擇好圖表類型並把 Chart 放入 Layout 後，第一步是將資料放入 Chart 中。


---
<br><br>
<div id="圖表類型與Layout設定"></div>
## 圖表類型 與 Layout 設定
在 xml 上放上 Chart View
```java
    <com.github.mikephil.charting.charts.LineChart
        android:id="@+id/chart"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
`<com.github.mikephil.charting.charts.LineChart` 的 `LineChart` 也可以更換其他種圖表類型，圖表類型有：
1. **摺線圖 LineChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056445988.png)
2. **長條圖 BarChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056370327.png)
3. **長條摺線圖 Combined-Chart**  ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056347376.png)
4. **圓餅圖 PieChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056415729.png)
5. **雷達圖 ScatterChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056465848.png)
6. **K線圖 CandleStickChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056571061.png)
7. **泡泡圖 BubbleChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056582819.png)
8. **雷達圖 RadarChart** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447056847896.png)

> 接下來本篇以最基本的「摺線圖 LineChart」來做為範例。



---
<br><br>
<div id="Data設定與操作"></div>
## Data 設定與操作

Chart 用 `LineData` 作為封裝所有資料的物件，並有 `DataSet`、`Entry`、`XVals` 等不同的元件，以下圖 LineChart 來說明基本概念：

 ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.mpandroid-dataset.PNG)

 -  `LineData` 中的 `List<DataSet>` 有兩個 DataSet，每個 DataSet 都是一個類別，並由 `List<Entry>` 所組成，簡單來看，資料的組成會如下表：
| DataSet/Entry | Entry0 | Entry1 | Entry2 | Entry3 | Entry4 |
| :-------------: | :------: | :------: | :------: | :------: | :------: |
| DataSet A | 0| 2 | 4 | 6 | 8 |
| DataSet B | 0| 4 | 8 | 12 | 16 |

- 圖右則是實際上程式碼使用的 Method 階層，代表設定資料物件時的架構， `Entry` 是最小單位，負責記錄資料數列的值與順序
- 圖左下方綠色區塊的 XVals 則代表顯示 X 軸的座標 Label ，是一個 List<String>

有了基本概念後，我們就開始來實作程式碼啦！

```java
@Override
public void onCreate(Bundle savedInstanceState) {
  setContentView(R.layout.activity_linechart);
    mChart = (LineChart) findViewById(R.id.chart);

    /* 使用 setData 的方法設定 LineData 物件*/
    mChart.setData( getLineData() );
}

/* 將 DataSet 資料整理好後，回傳 LineData */
private LineData getLineData(){
    final int DATA_COUNT = 5;  //資料數固定為 5 筆

  // LineDataSet(List<Entry> 資料點集合, String 類別名稱)
    LineDataSet dataSetA = new LineDataSet( getChartData(DATA_COUNT, 1), "A");
    LineDataSet dataSetB = new LineDataSet( getChartData(DATA_COUNT, 2), "B");

    List<LineDataSet> dataSets = new ArrayList<>();
    dataSets.add(dataSetA);
    dataSets.add(dataSetB);

  // LineData(List<String> Xvals座標標籤, List<Dataset> 資料集)
    LineData data = new LineData( getLabels(DATA_COUNT), dataSets);

    return data;

}

/* 取得 List<Entry> 的資料給 DataSet */
private List<Entry> getChartData(int count, int ratio){

    List<Entry> chartData = new ArrayList<>();
    for(int i=0;i<count;i++){
      // Entry(value 數值, index 位置)
        chartData.add(new Entry( i*2*ratio, i));
    }
    return chartData;
}

/* 取得 XVals Labels 給 LineData */
private List<String> getLabels(int count){

    List<String> chartLabels = new ArrayList<>();
    for(int i=0;i<count;i++) {
        chartLabels.add("X" + i);
    }
    return chartLabels;
}
```



`LineData` 的 Baseclass 是 [ChartData](https://github.com/PhilJay/MPAndroidChart/wiki/The-ChartData-class)，用於封裝所有 Data 與  Styling Data，而不同圖表的類型，有不同的 Subclass，譬如長條圖是使用 `BarData`、`BarDataSet`、`BarEntry`等都是繼承 `ChartData`，雖不同物件，但實際上用法大同小異，可在 `mChart.setChartData()` 查看傳入的物件型態


<div id="新增/移除Data"></div>
### 新增/移除 Data
在操作 Data 上， `DataSet` 與 `ChartData` 類別都有新增或移除 Entry 的方法（[官方Wiki 參考](https://github.com/PhilJay/MPAndroidChart/wiki/Dynamic-&-Realtime-Data)）

<div id="ModifyDataSet"></div>
> **DataSet**

 - addEntry(Entry e) ：在 List 最後增加 Entry
 - removeFirst() ：移除第一個 Entry
 - removeLast() ：移除最後一個 Entry
 - removeEntry(Entry e) ：移除指定的 Entry Object
 - removeEntry(int xIndex) ：移除指定 Index 的 Entry

<div id="ModifyChartData"></div>
> **ChartData**

 - addEntry(Entry e, int dataSetIndex) ：新增 Entry 到指定的 DataSet
 - addDataSet(DataSet d) ：增加一個 DataSet
 - removeEntry(Entry e, int dataSetIndex) ：移除指定的 DataSet 一個 Entry
 - removeEntry(int xIndex, int dataSetIndex) ：移除指定的 DataSet 中的某 Index 的 Entry
 - removeDataSet(DataSet d) ：移除一個 DataSet
 - removeDataSet(int index) ：移除指定 Index 的 DataSet

資料操作完後，切記必須使用 `notifyDataSetChanged()` 與 `invalidate()` 方法來刷新 View
``` java
mChart.notifyDataSetChanged();
mChart.invalidate();
```

</br>
</br>
<div id="DataStyle"></div>
### Data Style
> **Color**

設定此資料類別的基礎顏色

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447388407599.png)

```java
dataSet.setColor( getResources().getColor(R.color.pink) );
```
如果你有選色障礙的話，MPAndroid 有提供幾個系列色票， `ColorTemplate` 有以下五個 Static 的 Color Array 可以直接使用：
1. LIBERTY_COLORS：<span style="color:#CFF8F6;"> ▉ </span> , <span style="color:#94D4D4;"> ▉ </span> , <span style="color:#88B4BB;"> ▉ </span> , <span style="color:#76AEAF;"> ▉ </span> , <span style="color:#2A6D82;"> ▉ </span>
2. JOYFUL_COLORS：<span style="color:#D9508A;"> ▉ </span> , <span style="color:#FE9507;"> ▉ </span> , <span style="color:#FEF778;"> ▉ </span> , <span style="color:#6AA786;"> ▉ </span> , <span style="color:#35C2D1;"> ▉ </span>
3. PASTEL_COLORS：<span style="color:#405980;"> ▉ </span> , <span style="color:#95A57C;"> ▉ </span> , <span style="color:#D9B8A2;"> ▉ </span> , <span style="color:#BF8686;"> ▉ </span> , <span style="color:#B33050;"> ▉ </span>
4. COLORFUL_COLORS：<span style="color:#C12552;"> ▉ </span> , <span style="color:#FF6600;"> ▉ </span> , <span style="color:#F5C700;"> ▉ </span> , <span style="color:#6A961F;"> ▉ </span> , <span style="color:#B36435;"> ▉ </span>
5. VORDIPLOM_COLORS：<span style="color:#c0ff8c;"> ▉ </span> , <span style="color:#fff78c;"> ▉ </span> , <span style="color:#ffd08c;"> ▉ </span> , <span style="color:#8ceaff;"> ▉ </span> , <span style="color:#ff8c9d;"> ▉ </span>


<br>
<div id="DataPoint"></div>
> **Data Point**

設定每個 Entry 出現點的樣式

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447390965758.png)

`dataSet.setDrawCircles( boolean )` 預設為 True，可以直接設定 Data Point 的樣式
```java
dataSet.setCircleSize(10f);
dataSet.setCircleColor(ColorTemplate.JOYFUL_COLORS[1]);
```

`dataSet.setDrawCircleHole( boolean )`
False = 實心的單一顏色
True = 如範例圖並可以設定 `setCircleColorHole`的顏色
```java
dataSetC.setDrawCircleHole(false);
dataSet.setCircleColorHole(Color.WHITE);
```

<br>
<div id="DataLine"></div>
> **Line**

設定畫出資料線條的樣式

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447403990447.png)
```java
dataSet.setLineWidth(5f);
```
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447643754807.png)
```java
dataSet.setDrawCubic(true); //改成平滑曲線
dataSet.enableDashedLine(10f, 10f, 0f); //設定虛線（線條長度, 間隔空間, 角度）
// dataSet.setCubicIntensity(20f);//曲線彎曲強度
```


<br>
<div id="Highlight"></div>
> **Hightlight**

Highlight 是當點下 DataPoint 的時候，會在背景 Grid 線上出現一條用來對齊數值的指引線，預設是水平跟垂直都會顯示

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447411694105.png)

設定線條寬度與顏色
```java
dataSet.setHighlightLineWidth(3f);
dataSet.setHighLightColor(ColorTemplate.JOYFUL_COLORS[4]);
```

如果要設置只有垂直或水平單一條 Highlight 的話，可以先把 `setDrawHighlightIndicators( boolean )` 設為 False，再透過 `setDrawVerticalHighlightIndicator( boolean )` 或 `setDrawHorizontalHighlightIndicator( boolean )` 來打開單邊 Hightlight 線
```java
// 關閉 highlight 線
dataSet.setDrawHighlightIndicators(false);
// 設置垂直線
dataSet.setDrawVerticalHighlightIndicator(true);
```


<br>
<div id="Fill"></div>
> **Fill**

在摺線圖的一個資料類別中，把資料線下的面積填滿

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447406167644.png)

`dataSet.setDrawFilled( boolean )` 預設是 False，要打開才會有 Fill 作用，而最後加入的 `dataset` ，他的 z-index 為最大，會顯示在最上層
```java
dataSet.setDrawFilled(true);
dataSet.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
dataSet.setFillAlpha(100); //沒設定的話，alpha 預設為85，範圍是 0-255
```

<br>
<div id="ValueFormatter"></div>
 > **ValueFormatter**

重新格式化數值 Label 中的文字

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447412380204.png)

ValueFormat 可以設定給 `dataset`（單一資料類別）或 `Linedata` （所有資料），除了自定義 ValueFormatter 外，作者也提供兩個已定義好的 ValueFormatter，如：
 1. LargeValueFormatter：超過1000會變1k
 2. PercentFormatter：顯示成百分比，常用於 PieChart（百分比的數字要自己算好）
```java
dataSet.setValueFormatter(new PercentFormatter());
```

或你可以自訂義一個 ValueFormatter，如下範例：
```java
public class MyValueFormatter implements ValueFormatter {
    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("$###,###,##0.0"); // use one
        /* NumberFormat 是以每三位數加上逗號的格式化
         * DecimalFormat 是可以自定義顯示的數字格式
         * # : digit 不顯示零
         * 0 : digit 會補零
         * @ : 顯示幾位, ex. 12345[@@@]=12300
         * format 用法可以參考以下網址
         * http://developer.android.com/intl/zh-tw/reference/java/text/DecimalFormat.html
         */
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        /* ValueFormatter 一定要實作此方法
        * 此方法會把 value 跟 entry 傳過來，在這邊可以做一些邏輯上的處理
        * 回傳的 format 字串將會顯示在 Label 上 */
        return mFormat.format("$" + value);
    }
}
```

<br>
 > **Notes**

 XAxis 跟 YAxis 也可以設定 ValueFormatter，若要自定義 ValueFormatter 的話，需要實作 `YAxisValueFormatter` 或 `XAxisValueFormatter`


---
<br><br>

<div id="Axis軸線設定"></div>
##Axis軸線設定
Axis 分為 X 軸與 Y 軸的設定，取得 `Axis` 物件方法如下：
 - 取得 X 軸物件 `XAxis`
```java
XAxis mXAxis = mChart.getXAxis();
```

 - 取得 Y 軸物件 `YAxis`，有分為左右兩個：
     - Left 是影響由左至右的橫線 ，左邊的 Label
     - Right 是影響由右至左的橫線，右邊的 Label
```java
// Method 1
YAxis mLeftYAxis = mChart.getAxisLeft();
YAxis mRightYAxis = mChart.getAxisRight();
// Method 2
YAxis mLeftYAxis = mChart.getAxis(YAxis.AxisDependency.LEFT);
YAxis mRightYAxis = mChart.getAxis(YAxis.AxisDependency.RIGHT);
```
<br>
> **Notes**

如果不想要出現任何 X 或 Y 軸的東西，可以 `setEnabled(false)` 來關閉軸線


---
<br>
<div id="XAxis"></div>
### XAxis

<div id="XAxisPosition"></div>
> **Position**

設置 mXAxis 的位置，可以選擇以下幾個位置：
1. `TOP`
2. `TOP_INSIDE`
3. `BOTTOM`（預設）
4. `BOTTOM_INSIDE`
5. `BOTH_SIDED`
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447905337638.png)


```java
mXAxis.setPosition(XAxis.XAxisPosition.TOP);
```

<br>
<div id="XAxisLabel"></div>
> **Label**

設定 Label 顯示方式，以下介紹主要 Label 的設定：
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447905861620.png)

```java
mXAxis.setDrawLabels(false); // 顯示 或 隱藏 label
mXAxis.setLabelsToSkip(1);  // 要略過幾個 Label 才會顯示一個 Label
mXAxis.resetLabelsToSkip(); //重新設定略過 Label 的設定
mXAxis.setSpaceBetweenLabels(20); // 每個欄位的間距，default=4
```

文字的設定分為 Color、Size、Typeface
```java
mXAxis.setTextColor(getResources().getColor(R.color.accent));
mXAxis.setTextSize(12);
mXAxis.setTypeface(...)
```

<br>
<div id="XAxisLine"></div>
> **Line**

設定 mXAxis 的線條樣式，有分兩種：

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447906553412.png)

`GridLine` 是負責畫出背景格線中的所有垂直線，可以設定 Color、Width、DashedLine
```
mXAxis.setGridColor(getResources().getColor(R.color.green));
mXAxis.setGridLineWidth(3);
mXAxis.enableGridDashedLine(10f, 5f, 0f); //設定虛線（線條長度, 間隔空間, 角度）
```
`AxisLine` 設定 mXAxis 最靠近數值 Label 的水平線
```java
mXAxis.setAxisLineWidth(5f);
mXAxis.setAxisLineColor(getResources().getColor(R.color.second_accent));
```
如果不想顯示線條的話，也可以選擇關閉
```java
//圖表的背景線 GridLine
mXAxis.setDrawGridLines(false);

//最靠近 Label 的垂直線 AxisLine
mXAxis.setDrawAxisLine(false);
```


---
<br><br>
<div id="YAxis"></div>
##YAxis
YAxis 因為由 Right 跟 Left 兩條線組成，所以會影響到 Y 軸 Label 數值的設定，大部分都需要兩邊同時設定，否則兩邊顯示的數值會不一樣，或是 Label 數值線沒有對齊等狀況

<div id="YAxisMaxMinValue"></div>
> **Max/MinValue**

設定 mYAxis 顯示 Label 數值的最大值與最小值

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447904522594.png)


需要先把 `mYAxis.setStartAtZero( boolean )` 設為 False，才可以設定上下限
```java
mLeftYAxis.setStartAtZero(false);
mRightYAxis.setStartAtZero(false);
// 設定最大值
mLeftYAxis.setAxisMaxValue(3);
mRightYAxis.setAxisMaxValue(3);
// 設定最小值
mLeftYAxis.setAxisMinValue(-5f); //如果 data 沒有到負數，就不會看到 min value 的效果囉
mRightYAxis.setAxisMinValue(-5f);
```
可以用 reset 還原上下限
```java
mLeftYAxis.resetAxisMaxValue();
mRightYAxis.resetAxisMaxValue();
```

或是有特殊需求，只顯示最大/小值，可以設定 `setShowOnlyMinMax`，不過這個不受`setStartAtZero`的影響，不需要設為 False
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447651318422.png)
```java
mLeftYAxis.setShowOnlyMinMax(true);
mRightYAxis.setShowOnlyMinMax(true);
```

<br>
<div id="YAxisOrder"></div>
> **Order**

設定 Lebel 數值要 ASC 或 DESC 排序
```java
mLeftYAxis.setInverted(true);
mRightYAxis.setInverted(true);
```

<br>
<div id="YAxisSpace"></div>
> **Space**

Space 是設定資料之最大值與最小值的向外增加空間
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447907495206.png)

Top 為正值所設定多出來的向上空間
```java
mLeftYAxis.setSpaceTop(100f);
mRightYAxis.setSpaceTop(100f);
```
Bottom 為負值所設定多出來的向下空間
```java
mLeftYAxis.setSpaceBottom(100f);
mRightYAxis.setSpaceBottom(100f);
```

<br>
<div id="YAxisPosition"></div>
> **Position**

決定 Label 要顯示在 Chart 內還是外，有兩種：
1. `OUTSIDE_CHART`（預設）
2. `INSIDE_CHART`

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447907628076.png)

```java
mRightYAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
mLeftYAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
```

<br>
<div id="YAxisCount"></div>
> **Count**

`setLabelCount( int count, boolean force)`：設置顯示幾個 Label 數，第二個選項 Force，有一些差異：
1. Ture ：代表會精確的平均數值，均勻分配 Label
`Interval = [(Max - Min) / (Count - 1)]`

2. False：以算出間距來慢慢加上數值，不一定均勻分配 Label
`Interval = 透過 [(Max - Min) / Count] 算出 Range 後，再經過 log 跟小數的處理後得出`
差異在於這個方法不一定會顯示 chart 頂端的最大數值，沒有設定的話，一般圖表預設都是以這個方式顯示

一般沒有設定的話，Label 數量 Default = 6，最大可以設定 25 個

```java
mLeftYAxis.setLabelCount(7, true);
mRightYAxis.setLabelCount(7, true);
```

<br>
<div id="YAxisOffset"></div>
> **Offset**

如果有時候資料的第一個跟最後一個 Label 會與 mYAxis 的數值 Label 擋住的話，可以設定數值 Label 的 Offset，調整數值 Label 的距離

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1448016923008.png)

```java
// 設定 X 軸 Offset
mLeftYAxis.setXOffset(40);
mRightYAxis.setXOffset(15);

// 設定 Y 軸 Offset
mLeftYAxis.setYOffset(10);
mRightYAxis.setYOffset(10);
```

<br>
<div id="YAxisLine"></div>
> **Line**

設定 mYAxis 的線條樣式，有分兩種：

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447908732149.png)

`GridLine`負責畫出圖表背景格線的所有水平線
```java
//寬度
mLeftYAxis.setGridLineWidth(5f);
mRightYAxis.setGridLineWidth(5f);
//顏色
mLeftYAxis.setGridColor(getResources().getColor(R.color.green));
mRightYAxis.setGridColor(getResources().getColor(R.color.green));
//設定虛線
mLeftYAxis.enableGridDashedLine(20f, 5f, 0f); //設定虛線（線條長度, 間隔空間, 角度）
mRightYAxis.enableGridDashedLine(20f, 5f, 0f); //設定虛線（線條長度, 間隔空間, 角度）
```

`AxisLine` 為最靠近數值 Label 的垂直線
```java
//寬度
mLeftYAxis.setAxisLineWidth(5f);
mRightYAxis.setAxisLineWidth(5f);
//顏色
mLeftYAxis.setAxisLineColor(getResources().getColor(R.color.second_accent));
mRightYAxis.setAxisLineColor(getResources().getColor(R.color.second_accent));
```
如果不想顯示線條的話，也可以選擇關閉
```java
//圖表的背景水平線 GridLine
mLeftYAxis.setDrawGridLines(false);
mRightYAxis.setDrawGridLines(false);

//最靠近 Label 的垂直線 AxisLine
mLeftYAxis.setDrawAxisLine(false);
mRightYAxis.setDrawAxisLine(false);
```

---
<br>
<div id="LimitLine"></div>
# LimitLine
![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447661808116.png)

可以在圖表上加上額外的線，表示限制或上限等等，在一開始產生 `LimitLine` 時會帶入 Value 與 String
 `new LimitLine( flaot value , String showString )`
 下一步再設定給 mXAxis 或 mYAxis，並設定樣式
**（特別注意如果是放在 XAxis 的話，value 是指從左數過來第幾個 Label）**
初始化與設定 Limit 外觀
```java
LimitLine ll = new LimitLine(3f, "Limit");
//線條顏色、寬度
ll.setLineColor(getResources().getColor(R.color.blue_gray));
ll.setLineWidth(7f);
```
說明文字的部分，除了設定文字顏色、大小外，還可以設定文字的在 Limit 線的相對位置，有分為：
1. `LEFT_TOP`
2. `LEFT_BOTTOM`
3. `RIGHT_TOP`
4. `RIGHT_BOTTOM`

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447909908249.png)



```java
//文字位置
ll.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM)
//文字顏色、大小
ll.setTextColor(getResources().getColor(R.color.blue_gray));
ll.setTextSize(12f);
```

加入到 mXAxis 或 mYAxis
```java
mXAxis.addLimitLine(ll);
mLeftYAxis.addLimitLine(ll); //YAxis可以只選一邊設定就好
```

<br>
> **Notes**

不管是 XAxis 或 YAxis ，他們`addLimitLine`丟進去的 `LimitLine` 物件都是參考到同一個，所以如果想偷懶把 `LimitLine` 修改樣式後再丟給其他 Axis 是不行的！！（過來人T_T）


---
<br><br>
<a id="Legend圖例設定"></div>
# Legend圖例設定
透過 `chart.getLegend()` 取得 `Legend` 物件，可以設定圖例的大小、形狀、尺寸外，也可以設定圖例區的行距、間距等等

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447911795374.png)

<br>
<a div="取得Legend圖例區與設定位置"></div>
> **取得 Legend圖例區與設定位置**

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447917024871.png)
（圖中的名稱因長度關係有省略一些文字，可以到`Legend.LegendPosition`查看 Static String）
在上圖中有12個位置可以設定外，還有一個 PieChart 專用的 `PIECHART_CENTER`
```java
Legend lg = chart.getLegend(); //取得 Legend 圖例區
lg.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); // 設定Legend 圖例區位置
```

<br>
<div id="圖例組屬性設定"></div>
> **圖例組屬性設定**

圖例的外型有三種可以設定：

1. **CIRCLE** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447917593570.png)
2. **SQUARE（預設）**![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447917650698.png)
3. **LINE** ![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447917665616.png)

```java
lg.setFormSize(10f); //圖例尺寸
lg.setForm(Legend.LegendForm.CIRCLE); //圖例外型

//設定說明文字大小與顏色
lg.setTextSize(12f);
lg.setTextColor(Color.GRAY);
```

<div id="圖例邊距"></div>
<br>
> **圖例邊距**

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447996334832.png)

```java
lg.setXEntrySpace(50f); //各圖例組的間距
lg.setYEntrySpace(50f); //各圖例組的行距
lg.setFormToTextSpace(50f); //圖例與說明文字中間的距離
lg.setWordWrapEnabled(true); //圖例區超過圖表的長寬時，True = 保留超過部分， False = 裁切超過的部分
```

<br>
<div id="圖例顏色組設置"></div>
> **圖例顏色組設置**

Replace：重新設定目前圖例的顏色，但顏色數量要跟原本資料 `dataSet` 數量一樣，否則會出錯
`lg.setCustom( int[] colors, String[] labels)`
```java
lg.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "Set1", "Set2", "Set3"});
// 可以呼叫 resetCustom() 還原設定
lg.resetCustom();
```

Append：添加新的圖例
`lg.setExtra( int[] colors, String[] lebels)`
```java
lg.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[]{"Set1", "Set2", "Set3"});
```

<br>
> **Notes**

但這樣設定後，圖例跟資料的顏色設定就不一樣了，不太確定這種功能可以用在哪種狀況上XD


---
<br><br>
<div id="MakerView設置"></div>
## MakerView設置

![Alt text](https://github.com/25sprout/ChartLib-Demo-Android/blob/master/images/.1447921821625.png)

`MakerView` 是當點下 DataPoint 的時候，會跳出一個顯示資料的 `View`，原本預設點下 DataPoint 只有顯示 `Highlight` 而已，所以要先設計一個 `MakerView` 的 Layout，再撰寫一個物件繼承 `MakerView` 並覆寫原有的設計
```java
MyMakerView mv = new MyMakerView(this, R.layout.item_marker_view);
mChart.setMarkerView(mv);
```
`R.layout.item_maker_view`是只有一個 `TextView` 的 Layout，重點主要在於 `MyMakerView`物件所做的事

```java
public class MyMakerView extends MarkerView {

    private TextView tvContent;

    public MyMakerView (Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    /* 每次畫 MakerView 時都會觸發這個 Callback 方法，通常會在此方法內更新 View 的內容 */
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getVal());

    }

    /*
    * offset 是以點到的那個點作為 (0,0) 中心然後往右下角畫出來
    * 所以如果要顯示在點的上方
    * X=寬度的一半，負數
    * Y=高度的負數
     */

    @Override
    public int getXOffset() {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset() {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
```

另外附上 Layout 作為參考
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@drawable/circle_bg" >
    <TextView
        android:id="@+id/tvContent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:padding="5dp"
        android:text="1234567"
        android:textSize="12dp"
        android:textColor="@android:color/white"
        android:ellipsize="end"
        android:singleLine="true"
        android:textAppearance="?android:attr/textAppearanceSmall" />

</LinearLayout>
```

---
<br>
<div id="ViewPort調整"></div>
## ViewPort 調整
以下功能僅限於 `LineChart`, `BarChart`, `ScatterChart`, `CandleStickChart` 圖表使用，且在 `mChart.setData()` 之後呼叫，ViewPort 比較多是在跟調整 Chart View 有關係

<div id="ShowRange"></div>
> **Show Range**

跟 YAxis 設定 Max/Minimum 有點類似，不過出來的y座標值會有差異
```java
mChart.setVisibleXRangeMaximum(3);
mChart.setVisibleXRangeMinimum(1);
mChart.setVisibleYRangeMaximum(5, YAxis.AxisDependency.LEFT);
mChart.setVisibleYRangeMaximum(5, YAxis.AxisDependency.RIGHT);
```
<br>
<div id="Moving"></div>
> **Moving**

移動目前中心視圖到哪一個 X, Y 軸座標（方法會自動 invalidate）
```java
mChart.fitScreen(); //回復原本一開始的樣子
mChart.moveViewToX(float xIndex);
mChart.moveViewToY(float yValue, AxisDependency axis);
mChart.moveViewTo(float xIndex, float yValue, AxisDependency axis); // 至少會顯示一個 Point 的值
mChart.centerViewTo(int xIndex, float yValue, AxisDependency axis); // 以 xy 的座標軸為左邊中心
```
<br>
<div id="ZoomIn/Out"></div>
> **Zoom In/Out**

雖然用放大/縮小的手勢就可以 Zoom In/Out，但你也可以透過動態的方式來操作
```java
mChart.zoomIn();
mChart.zoomOut();
```

<br>
<div id="Offset"></div>
> **Offset**

設定圖表的 padding
```java
// chart 的 padding, 自動計算 padding 後再加上剛剛設定的 padding 值
mChart.setExtraOffsets(100, 100, 100, 100);

// 取消自動計算, 自己設定 padding 給他
mChart.setViewPortOffsets(100, 100, 100, 100);
mChart.resetViewPortOffsets();
```

> **Notes**

其實 ViewPort 是負責繪畫整個 View 的核心 Controller，可以透過 `chart.getViewPortHandler()`取得 Handler 物件，如果有要修改特殊地方的話，可以從 Handler 開始研究


---
<br><br>
<div id="BasicStyle"></div>
## Basic Style

關於 Chart 基本的設定

```java
// Background Color
mChart.setBackgroundColor(int color);
mChart.setDrawGridBackground(boolean enabled);
mChart.setGridBackgroundColor(int color);

// Description 說明的樣式
mChart.setDescription(String desc);
mChart.setDescriptionColor(int color);
mChart.setDescriptionPosition(float x, float y);
mChart.setDescriptionTypeface(Typeface t);
mChart.setDescriptionTextSize(float size);
mChart.setNoDataTextDescription(String desc);

// Border
mChart.setDrawBorders(boolean enabled);
mChart.setBorderColor(int color);
mChart.setBorderWidth(float width);
```

---
<br><br>
<div id="Animation"></div>
## Animation
使一開始圖表繪製的時候，會有動畫產生，可以選擇以下的動畫效果：
1.  X 軸（由左而右）`animateY( long millisecond , animationStyle )`
2.   Y 軸（由下而上）`animateX( long millisecond , animationStyle )`
3.  兩軸一起出現

Animation style 有很多種，可以到 `Easing.EasingOption`查看，並且記得設置完要呼叫 `invalidate()` 刷新
```java
chart.animateY(2000, Easing.EasingOption.Linear);
chart.animateX(2000, Easing.EasingOption.Linear);
chart.invalidate();
```
