import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function SelectSpecialite({ onNavigate, rdvData, setRdvData }) {
  const [specialites, setSpecialites] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");
    fetch(`${API}/api/specialites`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => res.json())
      .then(data => {
        const liste = Array.isArray(data) ? data : [];
        setSpecialites(liste);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  const handleSelect = (specialite) => {
    setRdvData({ ...rdvData, specialite });
    onNavigate("select-medecin");
  };

  const icones = {
    "Cardiologie": "❤️",
    "Pediatrie": "👶",
    "Dermatologie": "🧴",
    "Gynecologie": "👩",
    "Ophtalmologie": "👁️",
    "Neurologie": "🧠",
    "Medecine generale": "🩺",
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 700, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-hopital")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
        }}>
          Retour
        </button>

        {/* Progression */}
        <div style={{ display: "flex", alignItems: "center", gap: 8, marginBottom: 32 }}>
          {["Hopital", "Specialite", "Medecin", "Creneau", "Confirmation"].map((step, i) => (
            <div key={step} style={{ display: "flex", alignItems: "center", gap: 8 }}>
              <div style={{
                width: 28, height: 28, borderRadius: "50%",
                background: i <= 1 ? "#1a5fd4" : "#e2e8f0",
                color: i <= 1 ? "white" : "#94a3b8",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontSize: 12, fontWeight: 700,
              }}>
                {i < 1 ? "✓" : i + 1}
              </div>
              <span style={{ fontSize: 12, color: i <= 1 ? "#1a5fd4" : "#94a3b8", fontWeight: i === 1 ? 600 : 400 }}>
                {step}
              </span>
              {i < 4 && <div style={{ width: 20, height: 2, background: i < 1 ? "#1a5fd4" : "#e2e8f0" }} />}
            </div>
          ))}
        </div>

        {/* Hopital choisi */}
        <div style={{
          background: "white", borderRadius: 12, padding: "12px 16px",
          marginBottom: 24, display: "flex", alignItems: "center", gap: 12,
          boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
        }}>
          <span style={{ fontSize: 20 }}>🏥</span>
          <div>
            <div style={{ fontSize: 12, color: "#94a3b8" }}>Hopital choisi</div>
            <div style={{ fontWeight: 700, color: "#0f172a" }}>{rdvData?.hopital?.nom}</div>
          </div>
        </div>

        <h1 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          Choisissez une specialite
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Selectionnez la specialite medicale dont vous avez besoin
        </p>

        {loading ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>
            Chargement...
          </div>
        ) : specialites.length === 0 ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>
            Aucune specialite disponible
          </div>
        ) : (
          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
            {specialites.map(s => (
              <div key={s.idSpecialite} onClick={() => handleSelect(s)} style={{
                background: "white", borderRadius: 16, padding: 20,
                boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
                cursor: "pointer", border: "2px solid transparent",
                transition: "all .2s", textAlign: "center",
              }}
                onMouseEnter={e => e.currentTarget.style.border = "2px solid #1a5fd4"}
                onMouseLeave={e => e.currentTarget.style.border = "2px solid transparent"}
              >
                <div style={{ fontSize: 36, marginBottom: 8 }}>
                  {icones[s.nom] || "🩺"}
                </div>
                <div style={{ fontWeight: 700, fontSize: 15, color: "#0f172a" }}>{s.nom}</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}