package com.example.data.repository

import com.example.data.model.AgriMetric
import com.example.data.model.AgriPlanEntity
import com.example.data.model.DataSource
import com.example.data.model.MilestoneStatus
import com.example.data.model.PipelineStage
import com.example.data.model.RubricCriteria
import com.example.data.model.TimelineMilestone

object PrepopulatedAgriData {

    val publicDataSources: List<DataSource> = listOf(
        DataSource(
            id = "usda_nass",
            name = "USDA NASS QuickStats API",
            agency = "United States Department of Agriculture (NASS)",
            domain = "Production & Yields",
            description = "The premier comprehensive repository of agricultural production, acreage planted, harvest efficiency, crop progress condition ratings, and farm economic surveys.",
            publicUrl = "https://quickstats.nass.usda.gov/",
            apiEndpoint = "https://quickstats.nass.usda.gov/api/api_GET/",
            apiDocUrl = "https://quickstats.nass.usda.gov/api",
            accessProtocol = "REST API / JSON & Bulk CSV",
            updateFrequency = "Weekly & Annual",
            geographicCoverage = "US National, State, County, Agricultural Statistics Districts (ASD)",
            keyVariables = listOf("CROP YIELD (BU / ACRE)", "AREA PLANTED (ACRES)", "AREA HARVESTED (ACRES)", "CROP CONDITION (% EXCELLENT/GOOD)", "FARM GATE PRICE ($/BU)"),
            reliabilityRating = 4.9f,
            licensing = "US Government Public Domain (Free)",
            querySnippet = "curl 'https://quickstats.nass.usda.gov/api/api_GET/?key=API_KEY&commodity_desc=CORN&year__GE=2015&state_alpha=IA&format=JSON'"
        ),
        DataSource(
            id = "faostat",
            name = "FAOSTAT Global Agricultural Database",
            agency = "Food and Agriculture Organization of the United Nations (FAO)",
            domain = "Global Production & Balances",
            description = "Multi-decade standardized worldwide statistics on crop production, fertilizer consumption, agricultural land use, producer prices, and international food trade flows.",
            publicUrl = "https://www.fao.org/faostat/",
            apiEndpoint = "https://fenixservices.fao.org/faostat/api/v1/en/data/",
            apiDocUrl = "https://fenixservices.fao.org/faostat/api/v1/",
            accessProtocol = "REST API / JSON & Bulk Zip CSV",
            updateFrequency = "Annual & Bi-annual",
            geographicCoverage = "Global (245+ countries and territories)",
            keyVariables = listOf("Yield (hg/ha)", "Production (tonnes)", "Fertilizer Use (Nutrient N/P2O5/K2O)", "Producer Price Index", "Food Balance Sheets"),
            reliabilityRating = 4.8f,
            licensing = "Creative Commons Attribution 3.0 IGO",
            querySnippet = "curl 'https://fenixservices.fao.org/faostat/api/v1/en/data/QCL?area=all&item=56&element=5419&year=2020,2021,2022'"
        ),
        DataSource(
            id = "worldbank_pinksheet",
            name = "World Bank Commodity Markets (Pink Sheet)",
            agency = "World Bank Group - Development Prospects Group",
            domain = "Pricing & Commodities",
            description = "Monthly benchmark commodity prices for agriculture, fertilizers, energy, and metals with historical time series dating back to 1960 and real/nominal indices.",
            publicUrl = "https://www.worldbank.org/en/research/commodity-markets",
            apiEndpoint = "https://api.worldbank.org/v2/en/indicator/",
            apiDocUrl = "https://datahelpdesk.worldbank.org/knowledgebase/articles/889392-about-the-indicators-api-documentation",
            accessProtocol = "REST API / CSV / Excel Direct",
            updateFrequency = "Monthly (First week of month)",
            geographicCoverage = "Global benchmark trading hubs (US Gulf, Rotterdam, Bangkok, Black Sea)",
            keyVariables = listOf("Maize US Gulf ($/MT)", "Soybeans US CIF ($/MT)", "DAP Fertilizer ($/MT)", "Urea Black Sea ($/MT)", "Potassium Chloride ($/MT)"),
            reliabilityRating = 4.9f,
            licensing = "Creative Commons Attribution 4.0 (CC BY 4.0)",
            querySnippet = "import pandas as pd\ndf = pd.read_excel('https://thedocs.worldbank.org/en/doc/5d903e848db1d1b83e0ec8f744e55570-0350012021/related/CMO-Historical-Data-Monthly.xlsx', sheet_name='Monthly Prices')"
        ),
        DataSource(
            id = "cme_cbot",
            name = "CME Group / CBOT Agricultural Futures",
            agency = "Chicago Board of Trade / CME Group Open Data",
            domain = "Pricing Dynamics & Futures",
            description = "World benchmark forward pricing, open interest, commitment of traders, and term structure curves for Corn, Soybeans, Wheat, Rough Rice, and Soybean Meal.",
            publicUrl = "https://www.cmegroup.com/markets/agriculture.html",
            apiEndpoint = "https://quotes.investpy.com / AlphaVantage / Quandl CHRIS",
            apiDocUrl = "https://www.cmegroup.com/market-data.html",
            accessProtocol = "Daily Settlement CSV / Financial Data APIs",
            updateFrequency = "Real-time / End of Day (EOD)",
            geographicCoverage = "Global Benchmark Delivery Locations",
            keyVariables = listOf("Front-month Settlement Price", "6-Month Calendar Spread", "Open Interest Volume", "Implied Volatility Index", "Basis to Local Elevator"),
            reliabilityRating = 4.95f,
            licensing = "Delayed public quotes (15-min / Daily EOD free public research tier)",
            querySnippet = "curl 'https://www.alphavantage.co/query?function=CORN&interval=monthly&apikey=DEMO'"
        ),
        DataSource(
            id = "eurostat_agri",
            name = "Eurostat Agriculture & Fisheries Database",
            agency = "European Commission (Eurostat)",
            domain = "European Production & Input Costs",
            description = "Harmonized statistics across European Union member states covering agricultural prices, input cost indices, crop production accounts, and economic farm structures.",
            publicUrl = "https://ec.europa.eu/eurostat/web/agriculture/data/database",
            apiEndpoint = "https://ec.europa.eu/eurostat/api/dissemination/statistics/1.0/data/",
            apiDocUrl = "https://wikis.ec.europa.eu/display/EUROSTATHELP/API+Statistics+-+data+query",
            accessProtocol = "REST API / SDMX / JSON-stat",
            updateFrequency = "Monthly & Quarterly",
            geographicCoverage = "EU-27 Member States & Candidate Countries (NUTS-2 / NUTS-3)",
            keyVariables = listOf("Selling prices of crop products (apri_cp)", "Purchase prices of means of agricultural production (apri_ap)", "Harvested production in EU (apro_cpsh1)"),
            reliabilityRating = 4.8f,
            licensing = "European Union Open Data Policy (Free Reuse)",
            querySnippet = "curl 'https://ec.europa.eu/eurostat/api/dissemination/statistics/1.0/data/apri_ap_crpouta?format=JSON&lang=EN'"
        ),
        DataSource(
            id = "copernicus_era5",
            name = "Copernicus Agrometeorological Reanalysis (AgERA5)",
            agency = "European Centre for Medium-Range Weather Forecasts (ECMWF)",
            domain = "Climate & Agrometeorology",
            description = "Daily surface meteorological variables tailored for agricultural modeling: solar radiation, 2m temperature extremes, precipitation, relative humidity, and vapor pressure.",
            publicUrl = "https://cds.climate.copernicus.eu/",
            apiEndpoint = "https://cds.climate.copernicus.eu/api/v2",
            apiDocUrl = "https://cds.climate.copernicus.eu/api-how-to",
            accessProtocol = "Python CDS API / NetCDF4 / GRIB",
            updateFrequency = "Daily updates with 5-day lag",
            geographicCoverage = "Global Gridded (0.1 x 0.1 degree ~10km spatial resolution)",
            keyVariables = listOf("2m Temperature Max/Min (°C)", "Precipitation Flux (mm/day)", "Solar Radiation Flux (J/m²)", "Vapour Pressure Deficit (kPa)", "Growing Degree Days (GDD)"),
            reliabilityRating = 4.85f,
            licensing = "Copernicus Open Licence (Free for commercial and academic use)",
            querySnippet = "import cdsapi\nc = cdsapi.Client()\nc.retrieve('sis-agrometeorological-indicators', {'variable': '2m_temperature', 'statistic': '24_hour_mean', 'year': '2025', 'month': '06'}, 'download.nc')"
        ),
        DataSource(
            id = "usda_ers_wasde",
            name = "USDA ERS & WASDE Balance Sheets",
            agency = "USDA Economic Research Service & World Agricultural Outlook Board",
            domain = "Supply, Demand & Stock Elasticities",
            description = "The definitive gold standard monthly global supply and demand estimates: beginning stocks, domestic consumption, exports, ending stocks, and stocks-to-use ratios.",
            publicUrl = "https://www.usda.gov/oce/commodity/wasde",
            apiEndpoint = "https://api.nal.usda.gov/wasde/v1/",
            apiDocUrl = "https://www.ers.usda.gov/developer/data-apis/",
            accessProtocol = "JSON API / Monthly PDF & CSV tables",
            updateFrequency = "Monthly (Around 10th-12th of each month)",
            geographicCoverage = "Global major producing & importing nations",
            keyVariables = listOf("Stocks-to-Use Ratio (S/U %)", "Ending Stocks (Million Metric Tons)", "Total Domestic Disappearance", "Export Commitments", "Farm Season Average Price ($/bu)"),
            reliabilityRating = 4.95f,
            licensing = "US Government Open Data",
            querySnippet = "curl 'https://www.ers.usda.gov/webdocs/DataFiles/Feed_Grains_Yearbook_Tables.csv'"
        ),
        DataSource(
            id = "nasa_power_harvest",
            name = "NASA POWER Agroclimatology & Harvest Telemetry",
            agency = "NASA Langley Research Center & NASA Harvest",
            domain = "Satellite Remote Sensing & Energy Flux",
            description = "Surface meteorology and solar energy parameters customized for agricultural crop growth models, drought index tracking, and satellite NDVI anomaly monitoring.",
            publicUrl = "https://power.larc.nasa.gov/",
            apiEndpoint = "https://power.larc.nasa.gov/api/temporal/daily/point",
            apiDocUrl = "https://power.larc.nasa.gov/docs/services/api/",
            accessProtocol = "REST API / JSON / GeoJSON / CSV",
            updateFrequency = "Daily",
            geographicCoverage = "Global Gridded (0.5 x 0.5 degree)",
            keyVariables = listOf("ALLSKY_SFC_SW_DWN (Solar irradiance MJ/m²/day)", "T2M (Temperature at 2m)", "PRECTOTCORR (Precipitation corrected mm)", "RH2M (Relative humidity %)", "GWETTOP (Surface soil wetness fraction)"),
            reliabilityRating = 4.75f,
            licensing = "NASA Open Data (Free public domain)",
            querySnippet = "curl 'https://power.larc.nasa.gov/api/temporal/daily/point?parameters=T2M,PRECTOTCORR&community=AG&longitude=-93.5&latitude=42.0&start=20250101&end=20250601&format=JSON'"
        ),
        DataSource(
            id = "un_comtrade",
            name = "UN Comtrade International Trade Database",
            agency = "United Nations Statistics Division",
            domain = "Macro & Trade Dynamics",
            description = "Detailed bilateral international trade statistics broken down by HS commodity code (HS-1005 Maize, HS-1201 Soybeans, HS-3102 Mineral Nitrogenous Fertilizers).",
            publicUrl = "https://comtradeplus.un.org/",
            apiEndpoint = "https://comtradeapi.un.org/public/v1/preview/C/A/HS",
            apiDocUrl = "https://unstats.un.org/wiki/display/comtrade/Comtrade+API",
            accessProtocol = "REST API / Subscription Key (Free Tier)",
            updateFrequency = "Monthly & Annual",
            geographicCoverage = "Global (Over 200 reporter countries)",
            keyVariables = listOf("Export Trade Value ($ USD)", "Net Weight Quantity (kg)", "Trade Volume Unit Price ($/MT)", "Partner Country Flow Destination"),
            reliabilityRating = 4.7f,
            licensing = "UN Data License (Free Non-commercial & Research)",
            querySnippet = "curl 'https://comtradeapi.un.org/public/v1/preview/C/A/HS?reporterCode=842&cmdCode=1005&period=2024'"
        )
    )

    val keyMetrics: List<AgriMetric> = listOf(
        // Production & Yields
        AgriMetric(
            id = "metric_yield_per_ha",
            category = "Crop Yields & Acreage",
            name = "Average Yield per Harvested Area",
            code = "Y_HA",
            unit = "Metric Tons per Hectare (MT/ha) or Bushels/Acre (bu/ac)",
            formulaOrMethod = "Y_HA = Total Realized Crop Production (MT) / Total Area Harvested (ha)",
            description = "Primary volumetric measure of agricultural land productivity. Reflects genetic improvements, agronomic management, agroclimatic stress, and soil fertility.",
            agribusinessSignificance = "Core supply-side variable. A 3% downward revision in major exporter yield (e.g. US Midwest or Brazil Cerrado) shifts global balance sheets into deficit, triggering multi-week price rallies.",
            recommendedSources = listOf("usda_nass", "faostat", "eurostat_agri"),
            defaultBenchmark = "Corn US Avg: 177.3 bu/ac (~11.1 MT/ha); Soybeans: 51.5 bu/ac (~3.4 MT/ha)"
        ),
        AgriMetric(
            id = "metric_acreage_abandonment",
            category = "Crop Yields & Acreage",
            name = "Crop Area Abandonment Rate",
            code = "ABAND_RATE",
            unit = "Percentage (%)",
            formulaOrMethod = "ABAND_RATE = [(Area Planted - Area Harvested) / Area Planted] * 100",
            description = "Quantifies the fraction of planted cropland unharvested due to catastrophic drought, hail, riverine flooding, or uneconomic grain market prices below harvesting costs.",
            agribusinessSignificance = "Leading indicator of severe regional climate stress or disaster losses before final USDA/FAO yield tallies are published.",
            recommendedSources = listOf("usda_nass", "nasa_power_harvest"),
            defaultBenchmark = "Normal baseline: 4-6%; Severe drought years (e.g. 2012): Spikes above 12-18%"
        ),
        AgriMetric(
            id = "metric_crop_condition_index",
            category = "Crop Yields & Acreage",
            name = "Crop Condition Index (CCI)",
            code = "CCI",
            unit = "Index Points (0 - 500 Scale)",
            formulaOrMethod = "CCI = (% Very Poor * 1) + (% Poor * 2) + (% Fair * 3) + (% Good * 4) + (% Excellent * 5)",
            description = "Standardized weekly survey aggregation rating vegetative health, canopy vigor, and moisture status throughout the critical reproduction and grain-fill phases.",
            agribusinessSignificance = "Monitored obsessively by commodity desk algorithms every Monday at 4 PM EST. Deviations of >15 index points trigger immediate CME futures repricing.",
            recommendedSources = listOf("usda_nass"),
            defaultBenchmark = "Healthy reproductive crop: 375 - 410 index points (approx 65-75% Good/Excellent)"
        ),
        AgriMetric(
            id = "metric_ndvi_anomaly",
            category = "Agrometeorology & Health",
            name = "Normalized Difference Vegetation Index (NDVI) Anomaly",
            code = "NDVI_ANOM",
            unit = "Z-score Anomaly (-3.0 to +3.0)",
            formulaOrMethod = "NDVI = (NIR - RED) / (NIR + RED); NDVI_ANOM = (NDVI_t - NDVI_mean) / NDVI_std",
            description = "Satellite remote-sensing metric calculated from optical bands (Sentinel-2 / MODIS) indicating photosynthetic chlorophyll activity compared against the 15-year baseline.",
            agribusinessSignificance = "Provides high spatial resolution (10m - 250m) objective ground verification independent of farmer self-reporting surveys.",
            recommendedSources = listOf("copernicus_era5", "nasa_power_harvest"),
            defaultBenchmark = "Z-score between -0.5 and +0.5 indicates normal vegetative biomass"
        ),

        // Pricing Dynamics & Market Trends
        AgriMetric(
            id = "metric_cash_futures_basis",
            category = "Pricing Dynamics & Futures",
            name = "Local Cash-to-Futures Basis Spread",
            code = "BASIS",
            unit = "Cents per Bushel (¢/bu) or $ per Metric Ton ($/MT)",
            formulaOrMethod = "BASIS = Local Cash Elevator Bid (Spot) - CME Active Futures Settlement Price",
            description = "The fundamental pricing wedge representing localized supply/demand balance, barge/rail freight differentials, river lock closures, and local storage capacity.",
            agribusinessSignificance = "Allows agribusiness merchandisers to hedge price risk on futures exchanges while managing localized cash margin opportunities.",
            recommendedSources = listOf("cme_cbot", "usda_nass"),
            defaultBenchmark = "Interior Midwest cash corn basis historically ranges between -35¢/bu to +15¢/bu"
        ),
        AgriMetric(
            id = "metric_stocks_to_use",
            category = "Pricing Dynamics & Futures",
            name = "Global Stocks-to-Use Ratio (S/U)",
            code = "S_U_RATIO",
            unit = "Percentage (%)",
            formulaOrMethod = "S_U_RATIO = [Ending Carryover Stocks / Total Annual Domestic Consumption] * 100",
            description = "The quintessential macroeconomic indicator of commodity abundance vs scarcity. Measures the buffer reserve available in storage to absorb future harvest shocks.",
            agribusinessSignificance = "Inversely correlated with price volatility. When global S/U drops below historical thresholds (<12% for corn), market prices enter super-inelastic parabolic regimes.",
            recommendedSources = listOf("usda_ers_wasde", "faostat"),
            defaultBenchmark = "Global Corn S/U normal: 22-26%; Critical scarcity threshold: <18%"
        ),
        AgriMetric(
            id = "metric_farm_gate_spread",
            category = "Pricing Dynamics & Futures",
            name = "Farm-Gate to Wholesale Transmission Spread",
            code = "FG_W_SPREAD",
            unit = "Percentage Margin (%)",
            formulaOrMethod = "FG_W_SPREAD = [(Wholesale Terminal Price - Farm-Gate Price) / Wholesale Terminal Price] * 100",
            description = "Measures the capture of value by intermediaries (grain elevators, processors, logistics cartels) between the farm gate and wholesale export terminals.",
            agribusinessSignificance = "Crucial for assessing market competitiveness, supply chain rent extraction, and farm household solvency across developing and developed agriculture.",
            recommendedSources = listOf("worldbank_pinksheet", "faostat", "eurostat_agri"),
            defaultBenchmark = "Competitive grain corridors: 8-14%; Distorted inland infrastructure: 25-40%"
        ),
        AgriMetric(
            id = "metric_annualized_volatility",
            category = "Pricing Dynamics & Futures",
            name = "Annualized Historical Price Volatility (HV-30)",
            code = "HV_30",
            unit = "Percentage (%)",
            formulaOrMethod = "HV = [StdDev(ln(P_t / P_{t-1})) * sqrt(252)] * 100",
            description = "Statistical dispersion of daily returns over a 30-day trading window, annualized across standard market trading days.",
            agribusinessSignificance = "Dictates agricultural options premiums, working capital requirements for hedging grain elevators, and commercial bank credit terms for growers.",
            recommendedSources = listOf("cme_cbot"),
            defaultBenchmark = "Grain market baseline: 18-24%; Crisis / weather-market spikes: 35-55%"
        ),

        // Resource Utilization & Input Costs
        AgriMetric(
            id = "metric_fertilizer_intensity",
            category = "Resource Utilization & Inputs",
            name = "Synthetic Fertilizer Application Intensity",
            code = "FERT_INT",
            unit = "Kilograms of Active Nutrient per Hectare (kg N-P-K / ha)",
            formulaOrMethod = "FERT_INT = Total Nutrient Applied (kg N + kg P2O5 + kg K2O) / Total Planted Cropland (ha)",
            description = "Aggregates mineral fertilizer utilization. Nitrogen drives vegetative biomass and protein content, phosphorus stimulates root development, and potassium bolsters stress resistance.",
            agribusinessSignificance = "Input costs represent 35-45% of variable operating expenditures. Volatile natural gas prices directly dictate synthetic urea costs, impacting planting decisions.",
            recommendedSources = listOf("faostat", "worldbank_pinksheet", "usda_nass"),
            defaultBenchmark = "Intensive Corn: 140-180 kg N/ha; Leguminous Soybeans: Low N (rhizobia fixation), 40-70 kg P/K"
        ),
        AgriMetric(
            id = "metric_fuel_machinery_cost",
            category = "Resource Utilization & Inputs",
            name = "Agricultural Fuel & Field Power Index",
            code = "FUEL_IDX",
            unit = "Liters of Diesel per Hectare (L/ha) & Dollar Cost ($/ha)",
            formulaOrMethod = "FUEL_COST = Sum(Field Operations Fuel Consumption L/ha * Local Bulk Off-Road Diesel $/L)",
            description = "Tracks fuel consumption across tillage, chemical spray application, planting, combining, and on-farm grain cart haulage.",
            agribusinessSignificance = "Direct cost exposure linked to crude oil markets. Higher fuel costs raise break-even farm gate prices and compress farmer profit margins.",
            recommendedSources = listOf("usda_ers_wasde", "eurostat_agri"),
            defaultBenchmark = "Standard conservation tillage: 60-85 L/ha diesel across full crop cycle"
        ),
        AgriMetric(
            id = "metric_irrigation_depletion",
            category = "Resource Utilization & Inputs",
            name = "Irrigation Water Withdrawal & Soil Moisture Deficit",
            code = "WATER_DEF",
            unit = "Millimeters of Water Deficit (mm) or m³ / ha",
            formulaOrMethod = "WATER_DEF = Crop Evapotranspiration (ET_c) - Effective Rainfall (P_eff) - Applied Irrigation (I)",
            description = "Quantifies water depletion relative to agronomic requirement across the critical silking/tasseling or pod-fill stages.",
            agribusinessSignificance = "Critical for evaluating aquifer depletion (e.g. Ogallala Aquifer), pumping energy expenses, and drought vulnerability under changing regional hydrology.",
            recommendedSources = listOf("copernicus_era5", "nasa_power_harvest"),
            defaultBenchmark = "Seasonal corn water requirement: 500-650 mm total evapotranspiration"
        ),
        AgriMetric(
            id = "metric_corn_fertilizer_ratio",
            category = "Resource Utilization & Inputs",
            name = "Corn-to-Urea Price Affordability Ratio",
            code = "CORN_UREA_RATIO",
            unit = "Ratio (Bushels of Corn needed to purchase 1 Ton of Urea)",
            formulaOrMethod = "CORN_UREA_RATIO = Wholesale Urea Price ($/Short Ton) / Cash Corn Price ($/Bushel)",
            description = "Economic affordability index comparing crop output price to primary fertilizer input cost. Measures the farmer's purchasing power.",
            agribusinessSignificance = "When ratio rises above 150:1, farmers aggressively reduce nitrogen application rates or rotate acreage away from corn toward less fertilizer-intensive soybeans.",
            recommendedSources = listOf("worldbank_pinksheet", "cme_cbot"),
            defaultBenchmark = "Historical average: 90 - 120 bu/ton; Unfavorable input squeeze: >140 bu/ton"
        ),

        // Trade & Macro
        AgriMetric(
            id = "metric_export_parity_price",
            category = "Trade & Macro Dynamics",
            name = "FOB Export Parity Price",
            code = "FOB_PARITY",
            unit = "USD per Metric Ton ($/MT FOB Port Terminal)",
            formulaOrMethod = "FOB_PARITY = Upriver Interior Cash Price + Barge/Rail Freight + Elevation/Inspection Terminal Fee",
            description = "The competitive landed offer price at major global loading gateways (US Gulf, Santos/Paranagua Brazil, Black Sea Chornomorsk).",
            agribusinessSignificance = "Determines whether an exporter can win international purchase tenders issued by importing state agencies (e.g. Egypt GASC, China Sinograin).",
            recommendedSources = listOf("worldbank_pinksheet", "cme_cbot", "un_comtrade"),
            defaultBenchmark = "Global competitive range: $190 - $240/MT FOB depending on harvest cycle"
        ),
        AgriMetric(
            id = "metric_ocean_freight_rate",
            category = "Trade & Macro Dynamics",
            name = "Panamax Bulk Grain Ocean Freight Rate",
            code = "FREIGHT_PANAMAX",
            unit = "USD per Metric Ton ($/MT)",
            formulaOrMethod = "Voyage Charter Cost (US Gulf to Qingdao, China) / Cargo Capacity (MT)",
            description = "Cost of transoceanic dry bulk maritime shipping for agricultural bulk grains.",
            agribusinessSignificance = "Fluctuations in bunker fuel costs and canal transit queues (Panama / Suez) alter the delivered CIF price spread between South American and North American exporters.",
            recommendedSources = listOf("worldbank_pinksheet", "un_comtrade"),
            defaultBenchmark = "US Gulf to Asia: $42 - $65/MT depending on Baltic Dry Index cycle"
        )
    )

    val pipelineStages: List<PipelineStage> = listOf(
        PipelineStage(
            stageNumber = 1,
            phaseName = "Data Collection & Ingestion",
            title = "Automated Multi-Source Extraction & Data Lake Ingestion",
            objective = "Architect resilient, automated data acquisition pipelines connecting to public agricultural, financial, and agrometeorological repositories.",
            technicalMethods = listOf(
                "Asynchronous REST API polling with exponential backoff and rate-limit compliance",
                "Automated FTP/SFTP ingestion for daily USDA/WASDE batch balance sheets",
                "Spatial geospatial clipping of AgERA5/NASA raster grids to specific agricultural crop district geometries (Shapefiles/GeoJSON)",
                "Cryptographic checksum validation (SHA-256) of raw bulk CSV and NetCDF files for ingestion provenance"
            ),
            toolsAndFrameworks = listOf("Python 3.11", "Requests / Aiohttp", "Pandas", "xarray / rioxarray", "DuckDB", "Apache Airflow / Prefect"),
            qualityAssuranceTasks = listOf(
                "Verify API response HTTP 200 and schema payload integrity",
                "Audit missing timestamp ranges across daily weather feeds",
                "Log API latency and maintain historical ingest manifests"
            ),
            inputArtifacts = "Public API keys, Source URL endpoints, Regional bounding polygon coordinates",
            outputDeliverables = "Raw Data Lake (Parquet/NetCDF archive with immutable timestamping and raw JSON landing zones)",
            keyPitfallsAndMitigations = "Rate limiting and upstream schema changes: Mitigate by implementing local caching, token-bucket rate limiters, and defensive Pydantic schema validation."
        ),
        PipelineStage(
            stageNumber = 2,
            phaseName = "Data Cleaning & Preprocessing",
            title = "Harmonization, Deflation & Statistical Imputation",
            objective = "Convert heterogeneous multi-agency data into clean, synchronized, high-quality analytical datasets free of outliers and temporal misalignment.",
            technicalMethods = listOf(
                "Unit standardization: Uniform conversion of bushels, cwt, metric tons, and quintals to standardized kg and metric tons",
                "Monetary inflation adjustment: Deflating historical commodity spot prices using US Producer Price Index (PPI - Farm Products) and CPI",
                "Currency exchange harmonization: Normalizing national currencies (BRL, EUR, ARS) to USD via IMF daily exchange rates",
                "Outlier detection: Identifying false sensor spikes or reporting typos using Rolling Median Absolute Deviation (MAD) and Interquartile Range (IQR)",
                "Agronomic imputation: Filling intermittent weather gaps using Akima cubic splines and spatial inverse distance weighting (IDW)"
            ),
            toolsAndFrameworks = listOf("Polars / NumPy", "Scikit-learn (IterativeImputer)", "SciPy (interpolate)", "Great Expectations", "DBT"),
            qualityAssuranceTasks = listOf(
                "Run automated null-check audits (zero tolerance for key pricing variables)",
                "Validate bounded variables (e.g. Crop Condition percentages must sum to 100%)",
                "Detect structural breaks caused by government statistical definition revisions"
            ),
            inputArtifacts = "Raw tabular CSVs, uncalibrated gridded rasters, historical exchange rate series",
            outputDeliverables = "Curated Gold-Tier Feature Store: Standardized daily/weekly multi-variate time-series database",
            keyPitfallsAndMitigations = "Imputing non-stationary pricing series with mean substitution corrupts variance: Use forward-fill with decay or Kalman filter smoothing instead."
        ),
        PipelineStage(
            stageNumber = 3,
            phaseName = "Exploratory & Feature Engineering",
            title = "Agronomic Feature Engineering & Seasonal Decomposition",
            objective = "Synthesize domain-specific agricultural indicators that encode biological crop growth physics and economic trading regimes.",
            technicalMethods = listOf(
                "Seasonal-Trend decomposition using LOESS (STL) to isolate multi-year trends from harvest seasonality",
                "Growing Degree Days (GDD) accumulation: GDD = max(0, (T_max + T_min)/2 - T_base) during reproductive stages",
                "Vapor Pressure Deficit (VPD) and Standardized Precipitation Evapotranspiration Index (SPEI) 30-day drought rolling sums",
                "Market Term Structure Curves: Calculating 1st-month to 3rd-month futures backwardation/contango spreads",
                "Stocks-to-Use lagged elasticity transformations and polynomial basis terms"
            ),
            toolsAndFrameworks = listOf("Statsmodels (STL/TSA)", "SciPy", "GeoPandas", "Shapely", "Feature-Engine"),
            qualityAssuranceTasks = listOf(
                "Check for temporal data leakage (ensure features use strictly strictly historical lag t-k information)",
                "Evaluate multicollinearity among weather metrics using Variance Inflation Factor (VIF < 5.0)",
                "Verify stationarity via Augmented Dickey-Fuller (ADF) and Phillips-Perron tests"
            ),
            inputArtifacts = "Cleaned daily weather, daily prices, weekly condition surveys, monthly WASDE balances",
            outputDeliverables = "Feature Matrix (X_train, X_test, y) with lagged time-windows and domain agronomic indices",
            keyPitfallsAndMitigations = "Lookahead bias in spatial smoothing: Ensure seasonal indices only compute historical moving medians without future harvest window data."
        ),
        PipelineStage(
            stageNumber = 4,
            phaseName = "Statistical & Econometric Modeling",
            title = "Econometric Equilibrium & Market Trend Forecasting",
            objective = "Quantify supply-demand elasticities, cointegration relationships, and macroeconomic transmission shocks across agribusiness markets.",
            technicalMethods = listOf(
                "Vector Error Correction Models (VECM) testing long-run cointegration between crude oil, fertilizer, and corn prices",
                "Structural Vector Autoregression (SVAR) to identify impulse response functions of extreme weather shocks on spot basis",
                "Seasonal AutoRegressive Integrated Moving Average with Exogenous Regressors (SARIMAX)",
                "Granger Causality testing to determine whether fertilizer price shocks lead or lag grain acreage reallocation",
                "Hedonic pricing models estimating localized cash elevator discounts based on distance to Mississippi river barge terminals"
            ),
            toolsAndFrameworks = listOf("Statsmodels", "R (vars / tseries / urca)", "PyFlux", "Linearmodels"),
            qualityAssuranceTasks = listOf(
                "Inspect residual autocorrelation using Ljung-Box Q-statistic",
                "Check parameter stability using CUSUM and Chow structural break tests",
                "Ensure impulse response functions satisfy theoretical microeconomic sign restrictions"
            ),
            inputArtifacts = "Lagged feature matrix, log-differenced price vectors, macro trade balance variables",
            outputDeliverables = "Fitted Econometric Model Objects, Elasticity Parameter Tables, 95% Confidence Forecast Intervals",
            keyPitfallsAndMitigations = "Spurious regression from non-stationary unit-root series: Strict first-differencing or cointegration rank verification via Johansen procedure."
        ),
        PipelineStage(
            stageNumber = 5,
            phaseName = "Machine Learning & Spatial Yield Prediction",
            title = "Supervised Machine Learning & Satellite Spatial Yield Forecasting",
            objective = "Deploy state-of-the-art non-linear gradient boosting and spatial models to forecast end-of-season crop yields before official government harvest releases.",
            technicalMethods = listOf(
                "Extreme Gradient Boosting (XGBoost) and LightGBM tuned via Bayesian hyperparameter optimization",
                "Long Short-Term Memory (LSTM) recurrent networks capturing sequential weather stress accumulation",
                "Spatial Random Forest Regressors utilizing satellite NDVI time-series to predict county-level bushels/acre",
                "SHAP (Shapley Additive exPlanations) values to explain whether temperature extremes or moisture deficits drove predicted yield drops",
                "Ensemble stacking: Blending econometric SARIMAX baselines with tree-based machine learning forecasts"
            ),
            toolsAndFrameworks = listOf("XGBoost", "LightGBM", "PyTorch / TensorFlow", "SHAP", "Optuna", "Scikit-Learn"),
            qualityAssuranceTasks = listOf(
                "Execute rigorous Walk-Forward (Purged & Embargoed) Time-Series Cross-Validation",
                "Evaluate Root Mean Squared Error (RMSE), Mean Absolute Percentage Error (MAPE), and Directional Accuracy (Hit Rate > 72%)",
                "Audit feature importance to prevent overfitting to unrepresentative freak years (e.g. 2020 pandemic anomaly)"
            ),
            inputArtifacts = "Engineered multi-modal feature set (gridded weather, satellite vegetation indices, survey ratings)",
            outputDeliverables = "Trained Yield & Market Trend Ensemble Models, Feature Importance Diagnostic Visualizations, Predicted Yield Maps",
            keyPitfallsAndMitigations = "K-fold random cross-validation leaks temporal trends: Must use rolling walk-forward expanding window cross-validation."
        ),
        PipelineStage(
            stageNumber = 6,
            phaseName = "Interpretation, Scenario Testing & Reporting",
            title = "Monte Carlo Stress Testing, Decision Support & Policy Briefings",
            objective = "Synthesize mathematical predictions into high-impact strategic intelligence, risk assessments, and executive agribusiness decision deliverables.",
            technicalMethods = listOf(
                "Monte Carlo simulation (10,000 iterations) modeling joint probability distributions of drought severity and fertilizer tariff spikes",
                "Scenario analysis: Bullish (Severe La Niña drought), Base Case (Trendline yields), and Bearish (Record bumper harvest)",
                "Value-at-Risk (VaR 95% / 99%) calculations for grain elevator margin calls and grower input budget exposure",
                "Automated document compilation: Generating structured executive memos with interactive visual charts, methodology citations, and audit appendices"
            ),
            toolsAndFrameworks = listOf("Plotly / Matplotlib", "Pandas", "ReportLab / Markdown / DOCX engine", "Streamlit / Dash"),
            qualityAssuranceTasks = listOf(
                "Validate scenario outputs against historical tail-risk events (1988, 2008, 2012 droughts)",
                "Review clarity of executive summaries for non-technical stakeholders (farmers, investors, policymakers)",
                "Verify all public data source attribution and licensing compliance"
            ),
            inputArtifacts = "Ensemble forecasts, sensitivity matrices, historical covariance tables",
            outputDeliverables = "Comprehensive Agribusiness Data Analysis Plan DOC Deliverable, Interactive Risk Dashboard, Executive Presentation Deck",
            keyPitfallsAndMitigations = "Presenting deterministic point forecasts creates false confidence: Always communicate probabilistic prediction bands (80% and 95% confidence intervals)."
        )
    )

    val defaultMilestones: List<TimelineMilestone> = listOf(
        TimelineMilestone(
            id = "ms_1",
            phaseNumber = 1,
            phaseName = "Phase 1: Scoping & Governance",
            taskTitle = "Define Research Rationale, Commodity Scope & KPI Matrix",
            startWeek = 1,
            durationWeeks = 2,
            deliverable = "Project Charter, Research Questions, and Approved Key Metric Taxonomy Document",
            responsibleRole = "Lead Agribusiness Analyst & Academic Advisor",
            status = MilestoneStatus.COMPLETED,
            riskNotes = "Scope creep across too many crop varieties: Restricted initial focus to corn and soybeans."
        ),
        TimelineMilestone(
            id = "ms_2",
            phaseNumber = 2,
            phaseName = "Phase 2: Data Sourcing & API Ingest",
            taskTitle = "Public Data Source Extraction & Automated Ingestion Scripts",
            startWeek = 3,
            durationWeeks = 3,
            deliverable = "Production Data Ingestion Scripts (USDA NASS, FAOSTAT, World Bank Pink Sheet) & Raw Parquet Archive",
            responsibleRole = "Data Engineer / Analytics Student",
            status = MilestoneStatus.COMPLETED,
            riskNotes = "API rate-limiting on high-frequency weather calls: Implemented local SQLite caching."
        ),
        TimelineMilestone(
            id = "ms_3",
            phaseNumber = 3,
            phaseName = "Phase 3: Cleaning & Preprocessing",
            taskTitle = "Data Harmonization, Deflation & Outlier Imputation Pipeline",
            startWeek = 6,
            durationWeeks = 2,
            deliverable = "Cleaned Master Analytical Dataset with Data Quality Validation Audit Report",
            responsibleRole = "Data Scientist / Quantitative Analyst",
            status = MilestoneStatus.IN_PROGRESS,
            riskNotes = "Missing historical fertilizer series: Substituted World Bank Pink Sheet for discontinued regional surveys."
        ),
        TimelineMilestone(
            id = "ms_4",
            phaseNumber = 4,
            phaseName = "Phase 4: Feature Engineering",
            taskTitle = "Agronomic Feature Synthesis (GDD, Soil Moisture, Basis Spreads)",
            startWeek = 8,
            durationWeeks = 2,
            deliverable = "Curated Feature Store Matrix with Stationarity & Cointegration Test Documentation",
            responsibleRole = "Agronomic Data Scientist",
            status = MilestoneStatus.IN_PROGRESS,
            riskNotes = "Multicollinearity between temperature indices: Selected growing degree days and vapor pressure deficit."
        ),
        TimelineMilestone(
            id = "ms_5",
            phaseNumber = 5,
            phaseName = "Phase 5: Econometric & ML Modeling",
            taskTitle = "Econometric Time-Series & Machine Learning Model Training",
            startWeek = 10,
            durationWeeks = 2,
            deliverable = "Trained SARIMAX, VECM, and XGBoost Forecasting Models with Cross-Validation Benchmark Logs",
            responsibleRole = "Lead Quantitative Analyst",
            status = MilestoneStatus.PENDING,
            riskNotes = "Overfitting on recent volatile post-COVID price cycles: Applied robust Walk-Forward Purged CV."
        ),
        TimelineMilestone(
            id = "ms_6",
            phaseNumber = 6,
            phaseName = "Phase 6: Validation & Stress Testing",
            taskTitle = "Out-of-Sample Backtesting & Monte Carlo Supply Shock Scenarios",
            startWeek = 12,
            durationWeeks = 1,
            deliverable = "Scenario Stress-Testing Model Runs (La Niña Drought, Input Cost Surge, Export Embargo)",
            responsibleRole = "Risk & Strategy Analyst",
            status = MilestoneStatus.PENDING,
            riskNotes = "Ensure extreme tail risks do not violate non-negativity constraints on prices."
        ),
        TimelineMilestone(
            id = "ms_7",
            phaseNumber = 7,
            phaseName = "Phase 7: Final Synthesis & DOC Export",
            taskTitle = "Comprehensive Plan Document Compilation & Final Deliverable Review",
            startWeek = 13,
            durationWeeks = 2,
            deliverable = "Final Formatted Agribusiness Data Analysis Planning DOC Deliverable with Visuals and Executive Summary",
            responsibleRole = "Full Project Team / Student Fellow",
            status = MilestoneStatus.PENDING,
            riskNotes = "Ensure rigorous formatting, complete citations, and rubric compliance prior to submission deadline."
        )
    )

    val rubricCriteriaList: List<RubricCriteria> = listOf(
        RubricCriteria(
            id = "rubric_clarity",
            title = "Clarity of the Plan & Logical Structure",
            weightPercent = 20,
            maxScore = 20,
            currentScore = 19,
            evaluationFocus = "Organization, coherence, clear progression from research question to data architecture and analytical deliverables.",
            excellentBenchmark = "Document exhibits seamless logical flow: Executive Summary -> Research Rationale -> Data Architecture -> Methodology -> Gantt Milestones -> Risk Governance. Headings and subheadings are impeccably numbered and structured.",
            commonWeaknesses = "Disjointed narrative; jumping straight into technical tools without establishing the agribusiness problem statement.",
            actionableGuidance = "Use standardized numbered section headers (1.0, 1.1, 2.0). Ensure each section begins with a clear objective and rationale before detailing technical steps."
        ),
        RubricCriteria(
            id = "rubric_depth_research",
            title = "Depth of Research & Data Source Granularity",
            weightPercent = 20,
            maxScore = 20,
            currentScore = 19,
            evaluationFocus = "Breadth and authenticity of public agribusiness data sources, understanding of update frequencies, licensing, and limitations.",
            excellentBenchmark = "Identifies authoritative public agencies (USDA NASS, FAOSTAT, World Bank Pink Sheet, Copernicus, CME). Explains exact API endpoints, variables, geographic granularity, and historical depth.",
            commonWeaknesses = "Vague statements such as 'we will download data from the internet' without naming specific agencies, endpoints, or variables.",
            actionableGuidance = "Explicitly cite agency names, sample API curl requests, data update cadence (e.g. weekly Monday 4 PM), and licensing terms (Open Data CC BY 4.0)."
        ),
        RubricCriteria(
            id = "rubric_rigor_methodology",
            title = "Rigor of Pipeline & Analytical Methodology",
            weightPercent = 20,
            maxScore = 20,
            currentScore = 18,
            evaluationFocus = "Soundness of data cleaning, outlier handling, agronomic feature engineering, statistical modeling (ARIMA, VECM, XGBoost), and validation.",
            excellentBenchmark = "Comprehensive 6-stage ETL and modeling architecture. Accounts for stationarity testing, currency deflation via PPI, seasonal STL decomposition, and purged walk-forward cross-validation.",
            commonWeaknesses = "Assuming raw data is immediately ready for machine learning; ignoring agricultural seasonality and autocorrelation.",
            actionableGuidance = "Detail specific mathematical formulations (e.g. GDD, Stocks-to-Use, Basis spread) and include outlier imputation rules before regression modeling."
        ),
        RubricCriteria(
            id = "rubric_timeline_feasibility",
            title = "Realistic Timeline & Milestone Estimation",
            weightPercent = 20,
            maxScore = 20,
            currentScore = 18,
            evaluationFocus = "Work Breakdown Structure (WBS), realistic duration allocation across phases, risk buffers, and clear deliverables.",
            excellentBenchmark = "12-16 week roadmap with granular 1-2 week task allocations. Highlights critical path dependencies, buffer periods for data pipeline debugging, and explicit deliverables.",
            commonWeaknesses = "Unrealistic scheduling (e.g. allocating only 1 day for complex data cleaning or claiming machine learning can be done without feature engineering).",
            actionableGuidance = "Include a visual Gantt chart representation with status indicators and assign responsible roles/time buffers for each milestone."
        ),
        RubricCriteria(
            id = "rubric_readability",
            title = "Document Readability, Formatting & Professionalism",
            weightPercent = 20,
            maxScore = 20,
            currentScore = 19,
            evaluationFocus = "Professional presentation, bulleted lists, summary tables, academic tone, and exportability to formatted DOC/PDF.",
            excellentBenchmark = "Impeccable academic/executive prose, bullet points for readability, structured markdown/DOC tables, clear mathematical notation, and complete bibliographic citations.",
            commonWeaknesses = "Dense unformatted walls of text without bolding, tables, or structured breaks.",
            actionableGuidance = "Structure the deliverable with clear executive summaries, callout boxes for key takeaways, and structured indicator comparison tables."
        )
    )

    val preloadedPlans: List<AgriPlanEntity> = listOf(
        AgriPlanEntity(
            id = 1L,
            title = "Global Grain & Oilseeds Market Trend & Price Elasticity Forecasting Plan",
            sector = "Cereal Grains & Oilseeds (Corn & Soybeans)",
            targetCommodities = "Corn (Zea mays L.), Soybeans (Glycine max L.)",
            studentAuthor = "Agribusiness Economics & Quantitative Research Fellow",
            institution = "International Center for Agricultural Market Analytics",
            submissionDate = "October 2026",
            executiveSummary = "This document presents a comprehensive, production-grade data collection and analytical research plan to forecast price trends, local cash-futures basis spreads, and yield variations for major global grains. Leveraging authoritative open data from USDA NASS, FAOSTAT, World Bank Commodities, and Copernicus AgERA5, the plan outlines an end-to-end 14-week execution strategy spanning automated ingestion, econometric cointegration modeling, and gradient boosted yield prediction.",
            researchRationale = "Global grain markets represent the cornerstone of the international food supply chain, human caloric intake, and livestock protein conversion. However, extreme weather volatility, geopolitical export disruptions, and surging synthetic fertilizer costs create severe pricing instability. Traditional static forecasting models fail to capture the complex non-linear feedbacks between localized agroclimatic moisture deficits and macroeconomic commodity balance sheets. A rigorous, automated data collection and forecasting pipeline is urgently needed to provide farmers, grain elevators, and agricultural policymakers with timely, actionable intelligence.",
            problemStatement = "How can multi-source public agricultural, financial, and meteorological datasets be synthesized into a predictive pipeline that accurately anticipates grain market price volatility and localized yield deviations 30 to 90 days prior to harvest?",
            researchQuestions = "1. To what extent do early-season vegetative NDVI anomalies and accumulated Growing Degree Days (GDD) predict final USDA county-level corn yields?\n2. What is the dynamic price transmission elasticity between international fertilizer input shocks (World Bank Pink Sheet) and local cash-to-futures basis spreads?\n3. How do global Stocks-to-Use (S/U) revisions published in monthly WASDE reports impact short-term Chicago Board of Trade (CBOT) implied volatility regimes?",
            selectedSourceIds = "usda_nass,faostat,worldbank_pinksheet,cme_cbot,copernicus_era5,usda_ers_wasde,nasa_power_harvest",
            selectedMetricIds = "metric_yield_per_ha,metric_crop_condition_index,metric_ndvi_anomaly,metric_cash_futures_basis,metric_stocks_to_use,metric_fertilizer_intensity,metric_corn_fertilizer_ratio,metric_export_parity_price",
            totalWeeks = 14,
            status = "Ready for Submission",
            customNotes = "Primary reference deliverable demonstrating full compliance with evaluation criteria across all 5 rubric pillars.",
            isPreloaded = true,
            updatedAt = System.currentTimeMillis()
        ),
        AgriPlanEntity(
            id = 2L,
            title = "Specialty Cash Crop & Tropical Agroforestry Climate Hedging Plan",
            sector = "High-Value Specialty Tropical Cash Crops",
            targetCommodities = "Coffee (Coffea arabica & robusta), Cocoa (Theobroma cacao)",
            studentAuthor = "Tropical Agribusiness & Trade Strategy Fellow",
            institution = "Global Institute for Sustainable Agricultural Supply Chains",
            submissionDate = "October 2026",
            executiveSummary = "A specialized analytical research plan designed to evaluate the impact of extreme El Niño/La Niña weather cycles and pest outbreaks on high-value perennial cash crops in West Africa and Central/South America. Utilizing remote sensing telemetry from Copernicus and FAOSTAT production accounts to construct forward price risk models.",
            researchRationale = "Specialty perennial crops require multi-year capital investment and cannot rapidly adjust acreage in response to annual price signals. Recent severe climate anomalies in Ivory Coast, Ghana, and Brazil have driven cocoa and coffee futures to historic multi-decade highs. Analyzing public satellite soil moisture telemetry and trade flows is vital for supply chain resilience.",
            problemStatement = "How can smallholder agroclimatic stress indicators derived from open satellite observations be operationalized into a hedging model for international confectionary and coffee trade desks?",
            researchQuestions = "1. What is the lag time between satellite-detected moisture deficit in cocoa-growing belts and international port arrival shortfalls?\n2. How effective are producer-price transmission floors in stabilizing farm-gate income during global commodity supply gluts?",
            selectedSourceIds = "faostat,worldbank_pinksheet,copernicus_era5,un_comtrade,nasa_power_harvest",
            selectedMetricIds = "metric_yield_per_ha,metric_farm_gate_spread,metric_annualized_volatility,metric_ndvi_anomaly,metric_ocean_freight_rate",
            totalWeeks = 12,
            status = "In Review",
            customNotes = "Focuses on smallholder supply chain stability and international commodity exchanges.",
            isPreloaded = true,
            updatedAt = System.currentTimeMillis() - 86400000L
        ),
        AgriPlanEntity(
            id = 3L,
            title = "Fertilizer Input Cost Shock & Farm Gate Profitability Forecasting Plan",
            sector = "Agricultural Inputs & Farm Financial Economics",
            targetCommodities = "Anhydrous Ammonia, Diammonium Phosphate (DAP), Potassium Chloride (MOP)",
            studentAuthor = "Agricultural Finance & Farm Management Researcher",
            institution = "Center for Farm Financial Resilience",
            submissionDate = "October 2026",
            executiveSummary = "An econometric research strategy to quantify the transmission of natural gas price shocks into nitrogenous fertilizer retail costs and the subsequent impact on crop rotation choices and grower net cash income.",
            researchRationale = "Mineral fertilizers constitute the single largest variable operating expense in modern mechanized crop production. Because synthetic nitrogen synthesis relies heavily on the Haber-Bosch process powered by natural gas, energy market upheavals ripple directly into farm balance sheets. Forecasting these input dynamics empowers growers to optimize nutrient management.",
            problemStatement = "How do global natural gas and phosphate mining restrictions impact regional fertilizer affordability ratios, and what are the downstream consequences for farm solvency and crop acreage allocation?",
            researchQuestions = "1. What is the price transmission speed from World Bank benchmark Black Sea urea to domestic retail farm suppliers?\n2. At what Corn-to-Urea price ratio do farmers systematically switch acreage from corn to soybeans?",
            selectedSourceIds = "worldbank_pinksheet,usda_nass,eurostat_agri,usda_ers_wasde",
            selectedMetricIds = "metric_fertilizer_intensity,metric_corn_fertilizer_ratio,metric_fuel_machinery_cost,metric_cash_futures_basis",
            totalWeeks = 10,
            status = "Draft",
            customNotes = "High utility for farm credit agencies and cooperative fertilizer retailers.",
            isPreloaded = true,
            updatedAt = System.currentTimeMillis() - 172800000L
        ),
        AgriPlanEntity(
            id = 4L,
            title = "Sub-Saharan Staple Food Security & Yield Gap Analysis Plan",
            sector = "Food Security & Smallholder Staples",
            targetCommodities = "White Maize, Sorghum, Cassava, Rice",
            studentAuthor = "Food Security Analytics Specialist",
            institution = "Development Economics & Agrometeorology Consortium",
            submissionDate = "October 2026",
            executiveSummary = "A comprehensive data analysis plan integrating FEWS NET vulnerability data, FAOSTAT crop balances, and NASA POWER agroclimatology to forecast regional food security deficits and localized price inflation.",
            researchRationale = "In developing agricultural economies, smallholder farmers depend on rainfed farming systems highly vulnerable to monsoon failures. Public early-warning data systems can anticipate regional food deficits months in advance, enabling humanitarian agencies and national governments to pre-position strategic grain reserves.",
            problemStatement = "How can open-access agrometeorological feeds be utilized to predict seasonal harvest failures and market price spikes in vulnerable smallholder agricultural systems?",
            researchQuestions = "1. How accurately can satellite precipitation anomalies predict sub-national maize yield deficits 60 days before harvest?\n2. What is the elasticity between localized staple grain shortages and regional urban food inflation?",
            selectedSourceIds = "faostat,copernicus_era5,nasa_power_harvest,un_comtrade",
            selectedMetricIds = "metric_yield_per_ha,metric_acreage_abandonment,metric_farm_gate_spread,metric_irrigation_depletion",
            totalWeeks = 14,
            status = "In Review",
            customNotes = "Aligned with UN Sustainable Development Goal 2 (Zero Hunger).",
            isPreloaded = true,
            updatedAt = System.currentTimeMillis() - 259200000L
        )
    )
}
