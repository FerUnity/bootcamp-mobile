package com.example.indicadoresmvp.model

enum class IndicadorNacionalEnumeration(val codigo: String, val nombre: String) {
    UF("uf", "Unidad de Fomento (UF)"),
    IVP("ivp", "Indice de Valor Promedio (IVP)"),
    IPC("ipc", "Indice de Precios al Consumidor (IPC)"),
    UTM("utm", "Unidad Tributaria Mensual (UTM)"),
    IMACEC("imacec", "Indice Macroeconómico de la Empresa Central (IMACEC)"),
    TPM("tpm", "Tasa Política Monetaria (TPM)"),
    LIBRA_COBRE("libra_cobre", "Libra de Cobre"),
    TASA_DESEMPLEO("tasa_desempleo", "Tasa de Desempleo")
}

enum class IndicadorInternacionalEnumeration(val codigo: String, val nombre: String) {
    DOLAR("dolar", "Dólar"),
    DOLAR_INTERCAMBIO("dolar_intercambio", "Dólar de Intercambio"),
    EURO("euro", "Euro"),
    BITCOIN("bitcoin", "Bitcoin")
}