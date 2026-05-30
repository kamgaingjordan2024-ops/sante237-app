import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function SelectHopital({ onNavigate, rdvData, setRdvData }) {
  const [hopitaux, setHopitaux] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    fetch(`${API}/api/hopitaux`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => res.json())
      .then(data => { setHopitaux(data); setLoading(false); })
      .catch(() => setLoading(false));
  }, []);

  const filtered = hopitaux.filter(h =>
    h.nom.toLowerCase().includes(search.toLowerCase())
  );

  const handleSelect = (hopital) => {
    setRdvData({ ...rdvData, hopital });
    onNavigate("select-specialite");
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>

      {/* Header */}
      <div style={{ maxWidth: 700, margin: "0 auto" }}>
        <button onClick={() => onNavigate("home")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
          display: "flex", alignItems: "center", gap: 6,
        }}>
          Retour
        </button>

        {/* Progression */}
        <div style={{ display: "flex", alignItems: "center", gap: 8, marginBottom: 32 }}>
          {["Hopital", "Specialite", "Medecin", "Creneau", "Confirmation"].map((step, i) => (
            <div key={step} style={{ display: "flex", alignItems: "center", gap: 8 }}>
              <div style={{
                width: 28, height: 28, borderRadius: "50%",
                background: i === 0 ? "#1a5fd4" : "#e2e8f0",
                color: i === 0 ? "white" : "#94a3b8",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontSize: 12, fontWeight: 700,
              }}>
                {i + 1}
              </div>
              <span style={{ fontSize: 12, color: i === 0 ? "#1a5fd4" : "#94a3b8", fontWeight: i === 0 ? 600 : 400 }}>
                {step}
              </span>
              {i < 4 && <div style={{ width: 20, height: 2, background: "#e2e8f0" }} />}
            </div>
          ))}
        </div>

        <h1 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          Choisissez un hopital
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Selectionnez l hopital ou vous souhaitez consulter
        </p>

        {/* Recherche */}
        <input
          value={search}
          onChange={e => setSearch(e.target.value)}
          placeholder="Rechercher un hopital..."
          style={{
            width: "100%", padding: "12px 16px", borderRadius: 12,
            border: "2px solid #e2e8f0", fontSize: 15, marginBottom: 24,
            background: "white",
          }}
        />

        {/* Liste */}
        {loading ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>
            Chargement...
          </div>
        ) : filtered.length === 0 ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>
            Aucun hopital trouve
          </div>
        ) : (
          <div style={{ display: "grid", gap: 16 }}>
            {filtered.map(h => (
              <div key={h.idHopital} onClick={() => handleSelect(h)} style={{
                background: "white", borderRadius: 16, padding: 20,
                boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
                cursor: "pointer", border: "2px solid transparent",
                transition: "all .2s",
                display: "flex", alignItems: "center", justifyContent: "space-between",
              }}
                onMouseEnter={e => e.currentTarget.style.border = "2px solid #1a5fd4"}
                onMouseLeave={e => e.currentTarget.style.border = "2px solid transparent"}
              >
                <div style={{ display: "flex", alignItems: "center", gap: 16 }}>
                  <div style={{
                    width: 48, height: 48, borderRadius: 12,
                    background: "linear-gradient(135deg, #dbeafe, #bfdbfe)",
                    display: "flex", alignItems: "center", justifyContent: "center",
                    fontSize: 24,
                  }}>🏥</div>
                  <div>
                    <div style={{ fontWeight: 700, fontSize: 16, color: "#0f172a" }}>{h.nom}</div>
                    <div style={{ fontSize: 13, color: "#64748b", marginTop: 2 }}>{h.adresse}</div>
                    <div style={{ fontSize: 12, color: "#94a3b8", marginTop: 2 }}>{h.telephone}</div>
                  </div>
                </div>
                <div style={{ color: "#1a5fd4", fontSize: 20 }}>›</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}