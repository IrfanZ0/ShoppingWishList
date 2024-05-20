package com.tritongames.shoppingwishlist.data.models.bestbuy


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("accessories")
    val accessories: List<Any>,
    @SerializedName("accessoriesImage")
    val accessoriesImage: Any,
    @SerializedName("activationCharge")
    val activationCharge: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("activeUpdateDate")
    val activeUpdateDate: String,
    @SerializedName("addToCartUrl")
    val addToCartUrl: String,
    @SerializedName("affiliateAddToCartUrl")
    val affiliateAddToCartUrl: Any,
    @SerializedName("affiliateUrl")
    val affiliateUrl: Any,
    @SerializedName("albumLabel")
    val albumLabel: Any,
    @SerializedName("albumTitle")
    val albumTitle: String,
    @SerializedName("alternateCategories")
    val alternateCategories: List<Any>,
    @SerializedName("alternateViewsImage")
    val alternateViewsImage: String,
    @SerializedName("angleImage")
    val angleImage: Any,
    @SerializedName("artistId")
    val artistId: Any,
    @SerializedName("artistName")
    val artistName: Any,
    @SerializedName("aspectRatio")
    val aspectRatio: Any,
    @SerializedName("backViewImage")
    val backViewImage: Any,
    @SerializedName("bestSellingRank")
    val bestSellingRank: Any,
    @SerializedName("bundledIn")
    val bundledIn: List<Any>,
    @SerializedName("buybackPlans")
    val buybackPlans: List<Any>,
    @SerializedName("carrierModelNumber")
    val carrierModelNumber: Any,
    @SerializedName("carrierPlan")
    val carrierPlan: Any,
    @SerializedName("carrierPlans")
    val carrierPlans: List<Any>,
    @SerializedName("carriers")
    val carriers: List<Any>,
    @SerializedName("categoryPath")
    val categoryPath: List<CategoryPath>,
    @SerializedName("classId")
    val classId: Int,
    @SerializedName("class")
    val classX: String,
    @SerializedName("clearance")
    val clearance: Boolean,
    @SerializedName("color")
    val color: String,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("contracts")
    val contracts: List<Any>,
    @SerializedName("crossSell")
    val crossSell: List<Any>,
    @SerializedName("customerReviewAverage")
    val customerReviewAverage: Double,
    @SerializedName("customerReviewCount")
    val customerReviewCount: Int,
    @SerializedName("customerTopRated")
    val customerTopRated: Boolean,
    @SerializedName("department")
    val department: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("depth")
    val depth: Any,
    @SerializedName("description")
    val description: Any,
    @SerializedName("devices")
    val devices: List<Any>,
    @SerializedName("digital")
    val digital: Boolean,
    @SerializedName("dollarSavings")
    val dollarSavings: Double,
    @SerializedName("earlyTerminationFees")
    val earlyTerminationFees: List<Any>,
    @SerializedName("energyGuideImage")
    val energyGuideImage: Any,
    @SerializedName("esrbRating")
    val esrbRating: Any,
    @SerializedName("familyIndividualCode")
    val familyIndividualCode: Any,
    @SerializedName("format")
    val format: Any,
    @SerializedName("freeShipping")
    val freeShipping: Boolean,
    @SerializedName("freeShippingEligible")
    val freeShippingEligible: Boolean,
    @SerializedName("frequentlyPurchasedWith")
    val frequentlyPurchasedWith: List<Any>,
    @SerializedName("friendsAndFamilyPickup")
    val friendsAndFamilyPickup: Boolean,
    @SerializedName("fulfilledBy")
    val fulfilledBy: Any,
    @SerializedName("genre")
    val genre: Any,
    @SerializedName("height")
    val height: Any,
    @SerializedName("homeDelivery")
    val homeDelivery: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("inStoreAvailability")
    val inStoreAvailability: Boolean,
    @SerializedName("inStoreAvailabilityText")
    val inStoreAvailabilityText: Any,
    @SerializedName("inStoreAvailabilityUpdateDate")
    val inStoreAvailabilityUpdateDate: String,
    @SerializedName("inStorePickup")
    val inStorePickup: Boolean,
    @SerializedName("includedItemList")
    val includedItemList: List<IncludedItem>,
    @SerializedName("itemUpdateDate")
    val itemUpdateDate: String,
    @SerializedName("largeFrontImage")
    val largeFrontImage: String,
    @SerializedName("largeImage")
    val largeImage: String,
    @SerializedName("leftViewImage")
    val leftViewImage: Any,
    @SerializedName("lengthInMinutes")
    val lengthInMinutes: Any,
    @SerializedName("linkShareAffiliateAddToCartUrl")
    val linkShareAffiliateAddToCartUrl: String,
    @SerializedName("linkShareAffiliateUrl")
    val linkShareAffiliateUrl: String,
    @SerializedName("listingId")
    val listingId: Any,
    @SerializedName("lists")
    val lists: List<Any>,
    @SerializedName("longDescription")
    val longDescription: String,
    @SerializedName("lowPriceGuarantee")
    val lowPriceGuarantee: Boolean,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("marketplace")
    val marketplace: Any,
    @SerializedName("mediaCount")
    val mediaCount: Any,
    @SerializedName("mediumImage")
    val mediumImage: String,
    @SerializedName("members")
    val members: List<Any>,
    @SerializedName("minutePrice")
    val minutePrice: String,
    @SerializedName("mobileUrl")
    val mobileUrl: String,
    @SerializedName("modelNumber")
    val modelNumber: String,
    @SerializedName("monoStereo")
    val monoStereo: Any,
    @SerializedName("monthlyRecurringCharge")
    val monthlyRecurringCharge: String,
    @SerializedName("monthlyRecurringChargeGrandTotal")
    val monthlyRecurringChargeGrandTotal: String,
    @SerializedName("mpaaRating")
    val mpaaRating: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("new")
    val new: Boolean,
    @SerializedName("numberOfPlayers")
    val numberOfPlayers: Any,
    @SerializedName("onSale")
    val onSale: Boolean,
    @SerializedName("onlineAvailability")
    val onlineAvailability: Boolean,
    @SerializedName("onlineAvailabilityText")
    val onlineAvailabilityText: Any,
    @SerializedName("onlineAvailabilityUpdateDate")
    val onlineAvailabilityUpdateDate: String,
    @SerializedName("orderable")
    val orderable: String,
    @SerializedName("originalReleaseDate")
    val originalReleaseDate: Any,
    @SerializedName("outletCenter")
    val outletCenter: Any,
    @SerializedName("parentalAdvisory")
    val parentalAdvisory: Any,
    @SerializedName("percentSavings")
    val percentSavings: String,
    @SerializedName("planCategory")
    val planCategory: Any,
    @SerializedName("planFeatures")
    val planFeatures: List<Any>,
    @SerializedName("planPrice")
    val planPrice: Any,
    @SerializedName("planType")
    val planType: Any,
    @SerializedName("platform")
    val platform: Any,
    @SerializedName("plot")
    val plot: Any,
    @SerializedName("preowned")
    val preowned: Boolean,
    @SerializedName("priceRestriction")
    val priceRestriction: Any,
    @SerializedName("priceUpdateDate")
    val priceUpdateDate: String,
    @SerializedName("priceWithPlan")
    val priceWithPlan: List<Any>,
    @SerializedName("productFamilies")
    val productFamilies: List<Any>,
    @SerializedName("productId")
    val productId: Any,
    @SerializedName("productTemplate")
    val productTemplate: String,
    @SerializedName("productVariations")
    val productVariations: List<Any>,
    @SerializedName("proposition65WarningMessage")
    val proposition65WarningMessage: Any,
    @SerializedName("proposition65WarningType")
    val proposition65WarningType: String,
    @SerializedName("protectionPlanDetails")
    val protectionPlanDetails: List<Any>,
    @SerializedName("protectionPlanHighPrice")
    val protectionPlanHighPrice: String,
    @SerializedName("protectionPlanLowPrice")
    val protectionPlanLowPrice: String,
    @SerializedName("protectionPlanTerm")
    val protectionPlanTerm: String,
    @SerializedName("protectionPlanType")
    val protectionPlanType: Any,
    @SerializedName("protectionPlans")
    val protectionPlans: List<Any>,
    @SerializedName("quantityLimit")
    val quantityLimit: Int,
    @SerializedName("regularPrice")
    val regularPrice: Double,
    @SerializedName("relatedProducts")
    val relatedProducts: List<Any>,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("remoteControlImage")
    val remoteControlImage: Any,
    @SerializedName("requiredParts")
    val requiredParts: List<Any>,
    @SerializedName("rightViewImage")
    val rightViewImage: Any,
    @SerializedName("salePrice")
    val salePrice: Double,
    @SerializedName("salesRankLongTerm")
    val salesRankLongTerm: Any,
    @SerializedName("salesRankMediumTerm")
    val salesRankMediumTerm: Any,
    @SerializedName("salesRankShortTerm")
    val salesRankShortTerm: Any,
    @SerializedName("score")
    val score: Any,
    @SerializedName("screenFormat")
    val screenFormat: Any,
    @SerializedName("secondaryMarket")
    val secondaryMarket: Any,
    @SerializedName("sellerId")
    val sellerId: Any,
    @SerializedName("shipping")
    val shipping: List<Shipping>,
    @SerializedName("shippingCost")
    val shippingCost: Double,
    @SerializedName("shippingLevelsOfService")
    val shippingLevelsOfService: List<ShippingLevelsOfService>,
    @SerializedName("shippingRestrictions")
    val shippingRestrictions: Any,
    @SerializedName("shippingWeight")
    val shippingWeight: Double,
    @SerializedName("shortDescription")
    val shortDescription: Any,
    @SerializedName("sku")
    val sku: Long,
    @SerializedName("softwareAge")
    val softwareAge: Any,
    @SerializedName("softwareGrade")
    val softwareGrade: Any,
    @SerializedName("softwareNumberOfPlayers")
    val softwareNumberOfPlayers: Any,
    @SerializedName("source")
    val source: Any,
    @SerializedName("specialOrder")
    val specialOrder: Boolean,
    @SerializedName("spin360Url")
    val spin360Url: Any,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("studio")
    val studio: Any,
    @SerializedName("studioLive")
    val studioLive: Any,
    @SerializedName("subclass")
    val subclass: String,
    @SerializedName("subclassId")
    val subclassId: Int,
    @SerializedName("techSupportPlans")
    val techSupportPlans: List<Any>,
    @SerializedName("technologyCode")
    val technologyCode: Any,
    @SerializedName("theatricalReleaseDate")
    val theatricalReleaseDate: Any,
    @SerializedName("thumbnailImage")
    val thumbnailImage: String,
    @SerializedName("topViewImage")
    val topViewImage: Any,
    @SerializedName("tradeInValue")
    val tradeInValue: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("upc")
    val upc: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("validFrom")
    val validFrom: Any,
    @SerializedName("validUntil")
    val validUntil: Any,
    @SerializedName("warrantyLabor")
    val warrantyLabor: String,
    @SerializedName("warrantyParts")
    val warrantyParts: String,
    @SerializedName("weight")
    val weight: Any,
    @SerializedName("width")
    val width: Any
)