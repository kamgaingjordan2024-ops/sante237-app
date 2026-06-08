import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function DashboardAdmin({ onNavigate }) {
  const [onglet, setOnglet] = useState("hopitaux");
  const [hopitaux, setHopitaux] = useState([]);
  const [medecins, setMedecins] = useState([]);
  const [patients, setPatients] = useState([]);
  const [rendezvous, setRendezvous] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");

  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };

  const fetchHopitaux = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/hopitaux`, { headers });
      if (res.ok) setHopitaux(await res.json());
    } catch { setError("Erreur chargement hôpitaux"); }
    finally { setLoading(false); }
  };

  const fetchMedecins = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/utilisateurs/medecins`, { headers });
      if (res.ok) setMedecins(await res.json());
    } catch { setError("Erreur chargement médecins"); }
    finally { setLoading(false); }
  };

  const fetchPatients = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/utilisateurs/patients`, { headers });
      if (res.ok) setPatients(await res.json());
    } catch { setError("Erreur chargement patients"); }
    finally { setLoading(false); }
  };

  const fetchRendezvous = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/rendezvous`, { headers });
      if (res.ok) setRendezvous(await res.json());
    } catch { setError("Erreur chargement rendez-vous"); }
    finally { setLoading(false); }
  };

  useEffect(() => {
    if (onglet === "hopitaux") fetchHopitaux();
    if (onglet === "medecins") fetchMedecins();
    if (onglet === "patients") fetchPatients();
    if (onglet === "rendezvous") fetchRendezvous();
  }, [onglet]);

  const handleDeleteHopital = async (id) => {
    if (!window.confirm("Supprimer cet hôpital ?")) return;
    try {
      const res = await fetch(`${API}/api/hopitaux/${id}`, { method: "DELETE", headers });
      if (res.ok) { setSuccess("Hôpital supprimé !"); fetchHopitaux(); }
    } catch { setError("Erreur suppression."); }
  };

  const handleLogout = () => {
    localStorage.clear();
    onNavigate("home");
  };

  const formatDate = (dt) => {
    if (!dt) return "";
    return new Date(dt).toLocaleDateString("fr-FR", {
      day: "numeric", month: "long", year: "numeric",
      hour: "2-digit", minute: "2-digit"
    });
  };

  const statutStyle = (statut) => {
    switch (statut) {
      case "PLANIFIE": return { background: "#dbeafe", color: "#1d4ed8" };
      case "CONFIRME": return { background: "#dcfce7", color: "#166534" };
      case "REJETE": return { background: "#fee2e2", color: "#dc2626" };
      default: return { background: "#f1f5f9", color: "#64748b" };
    }
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)" }}>

      {/* Header */}
      <div style={{
        background: "white", padding: "16px 24px",
        boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
        display: "flex", justifyContent: "space-between", alignItems: "center"
      }}>
        <div>
          <h1 style={{ fontSize: 20, fontWeight: 700, color: "#0f172a", margin: 0 }}>
            🛡️ Dashboard Administrateur
          </h1>
          <p style={{ fontSize: 12, color: "#64748b", margin: 0 }}>
            {user?.nom} {user?.prenom}
          </p>
        </div>
        <button onClick={handleLogout} style={{
          padding: "8px 16px", borderRadius: 8, border: "1px solid #e2e8f0",
          background: "white", color: "#64748b", cursor: "pointer", fontSize: 13
        }}>
          Déconnexion
        </button>
      </div>

      <div style={{ maxWidth: 900, margin: "32px auto", padding: "0 24px" }}>

        {/* Onglets */}
        <div style={{ display: "flex", gap: 8, marginBottom: 24, flexWrap: "wrap" }}>
          {[
            { key: "hopitaux", label: "🏥 Hôpitaux" },
            { key: "medecins", label: "👨‍⚕️ Médecins" },
            { key: "patients", label: "👤 Patients" },
            { key: "rendezvous", label: "📋 Rendez-vous" },
          ].map((o) => (
            <button key={o.key} onClick={() => setOnglet(o.key)} style={{
              padding: "10px 20px", borderRadius: 10, border: "none", cursor: "pointer",
              fontWeight: 600, fontSize: 14,
              background: onglet === o.key ? "linear-gradient(135deg, #1a5fd4, #0d3d8f)" : "white",
              color: onglet === o.key ? "white" : "#64748b",
              boxShadow: onglet === o.key ? "0 4px 12px rgba(26,95,212,0.3)" : "0 2px 4px rgba(0,0,0,0.06)",
            }}>
              {o.label}
            </button>
          ))}
        </div>

        {/* Messages */}
        {error && (
          <div style={{ padding: "12px 16px", borderRadius: 10, background: "#fee2e2", color: "#991b1b", marginBottom: 16 }}>
            {error}
          </div>
        )}
        {success && (
          <div style={{ padding: "12px 16px", borderRadius: 10, background: "#dcfce7", color: "#166534", marginBottom: 16 }}>
            {success}
          </div>
        )}

        {/* ===== HÔPITAUX ===== */}
        {onglet === "hopitaux" && (
          <div style={{ background: "white", borderRadius: 20, padding: 24, boxShadow: "0 4px 16px rgba(26,95,212,0.08)" }}>
            <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
              🏥 Hôpitaux ({hopitaux.length})
            </h2>
            {loading && <p>Chargement...</p>}
            {hopitaux.map((h) => (
              <div key={h.idHopital} style={{
                display: "flex", justifyContent: "space-between", alignItems: "center",
                padding: "14px 0", borderBottom: "1px solid #f1f5f9"
              }}>
                <div>
                  <div style={{ fontWeight: 600, color: "#0f172a" }}>{h.nom}</div>
                  <div style={{ fontSize: 12, color: "#64748b" }}>{h.adresse} • {h.telephone}</div>
                </div>
                <button onClick={() => handleDeleteHopital(h.idHopital)} style={{
                  padding: "6px 14px", borderRadius: 8,
                  border: "1px solid #fecaca", background: "#fee2e2",
                  color: "#dc2626", cursor: "pointer", fontSize: 12, fontWeight: 600
                }}>
                  Supprimer
                </button>
              </div>
            ))}
          </div>
        )}

        {/* ===== MÉDECINS ===== */}
        {onglet === "medecins" && (
          <div style={{ background: "white", borderRadius: 20, padding: 24, boxShadow: "0 4px 16px rgba(26,95,212,0.08)" }}>
            <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
              👨‍⚕️ Médecins ({medecins.length})
            </h2>
            {loading && <p>Chargement...</p>}
            {medecins.map((m) => (
              <div key={m.idUtilisateur} style={{
                padding: "14px 0", borderBottom: "1px solid #f1f5f9"
              }}>
                <div style={{ fontWeight: 600, color: "#0f172a" }}>Dr. {m.nom} {m.prenom}</div>
                <div style={{ fontSize: 12, color: "#64748b" }}>{m.email} • {m.telephone}</div>
              </div>
            ))}
          </div>
        )}

        {/* ===== PATIENTS ===== */}
        {onglet === "patients" && (
          <div style={{ background: "white", borderRadius: 20, padding: 24, boxShadow: "0 4px 16px rgba(26,95,212,0.08)" }}>
            <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
              👤 Patients ({patients.length})
            </h2>
            {loading && <p>Chargement...</p>}
            {patients.map((p) => (
              <div key={p.idUtilisateur} style={{
                padding: "14px 0", borderBottom: "1px solid #f1f5f9"
              }}>
                <div style={{ fontWeight: 600, color: "#0f172a" }}>{p.nom} {p.prenom}</div>
                <div style={{ fontSize: 12, color: "#64748b" }}>{p.email} • {p.telephone}</div>
              </div>
            ))}
          </div>
        )}

        {/* ===== RENDEZ-VOUS ===== */}
        {onglet === "rendezvous" && (
          <div style={{ background: "white", borderRadius: 20, padding: 24, boxShadow: "0 4px 16px rgba(26,95,212,0.08)" }}>
            <h2 style={{ fontSize: 18, fontWeight: 700, color: "#0f172a", marginBottom: 16 }}>
              📋 Tous les rendez-vous ({rendezvous.length})
            </h2>
            {loading && <p>Chargement...</p>}
            {rendezvous.map((r) => (
              <div key={r.id_rendezvous} style={{
                display: "flex", justifyContent: "space-between", alignItems: "center",
                padding: "14px 0", borderBottom: "1px solid #f1f5f9"
              }}>
                <div>
                  <div style={{ fontWeight: 600, color: "#0f172a", fontSize: 14 }}>
                    👤 {r.patientNom} → 👨‍⚕️ {r.medecinNom}
                  </div>
                  <div style={{ fontSize: 12, color: "#64748b", marginTop: 4 }}>
                    📅 {formatDate(r.dateHeure)} • ⏱️ {r.duree} min
                  </div>
                </div>
                <span style={{
                  padding: "4px 12px", borderRadius: 20, fontSize: 11, fontWeight: 700,
                  ...statutStyle(r.statut)
                }}>
                  {r.statut}
                </span>
              </div>
            ))}
          </div>
        )}

      </div>
    </div>
  );
}