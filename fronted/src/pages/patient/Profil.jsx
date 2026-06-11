import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function Profil({ onNavigate }) {
  const [form, setForm] = useState({ nom: "", prenom: "", email: "", telephone: "" });
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const userId = user?.id;

  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };

  useEffect(() => {
    setForm({
      nom: user?.nom || "",
      prenom: user?.prenom || "",
      email: user?.email || "",
      telephone: user?.telephone || "",
    });
  }, []);

  const handleSave = async () => {
    setLoading(true); setError(""); setSuccess("");
    try {
      const res = await fetch(`${API}/api/utilisateurs/${userId}`, {
        method: "PUT",
        headers,
        body: JSON.stringify(form),
      });
      if (res.ok) {
        const updated = await res.json();
        localStorage.setItem("user", JSON.stringify({ ...user, ...updated }));
        setSuccess("Profil mis à jour avec succès !");
      } else {
        setError("Erreur lors de la mise à jour.");
      }
    } catch { setError("Impossible de contacter le serveur."); }
    finally { setLoading(false); }
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 500, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-hopital")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
        }}>
          ← Retour
        </button>

        <h1 style={{ fontSize: 24, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          👤 Mon Profil
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Modifiez vos informations personnelles
        </p>

        <div style={{
          background: "white", borderRadius: 20, padding: 28,
          boxShadow: "0 4px 16px rgba(26,95,212,0.08)"
        }}>

          {/* Avatar */}
          <div style={{ textAlign: "center", marginBottom: 24 }}>
            <div style={{
              width: 80, height: 80, borderRadius: "50%",
              background: "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
              display: "flex", alignItems: "center", justifyContent: "center",
              fontSize: 32, margin: "0 auto 12px",
            }}>
              👤
            </div>
            <div style={{ fontWeight: 700, fontSize: 18, color: "#0f172a" }}>
              {form.nom} {form.prenom}
            </div>
            <div style={{
              display: "inline-block", padding: "4px 12px", borderRadius: 20,
              background: "#dbeafe", color: "#1d4ed8", fontSize: 12, fontWeight: 600, marginTop: 4
            }}>
              {user?.role}
            </div>
          </div>

          {/* Champs */}
          {[
            { label: "Nom", key: "nom" },
            { label: "Prénom", key: "prenom" },
            { label: "Email", key: "email", type: "email" },
            { label: "Téléphone", key: "telephone" },
          ].map((field) => (
            <div key={field.key} style={{ marginBottom: 16 }}>
              <label style={{ fontSize: 13, color: "#64748b", display: "block", marginBottom: 6, fontWeight: 600 }}>
                {field.label}
              </label>
              <input
                type={field.type || "text"}
                value={form[field.key]}
                onChange={(e) => setForm({ ...form, [field.key]: e.target.value })}
                style={{
                  width: "100%", padding: "10px 12px", borderRadius: 10,
                  border: "1px solid #e2e8f0", fontSize: 14, boxSizing: "border-box"
                }}
              />
            </div>
          ))}

          {error && (
            <div style={{ padding: "10px 14px", borderRadius: 10, background: "#fee2e2", color: "#991b1b", fontSize: 13, marginBottom: 16 }}>
              {error}
            </div>
          )}
          {success && (
            <div style={{ padding: "10px 14px", borderRadius: 10, background: "#dcfce7", color: "#166534", fontSize: 13, marginBottom: 16 }}>
              {success}
            </div>
          )}

          <button onClick={handleSave} disabled={loading} style={{
            width: "100%", padding: "14px", borderRadius: 12, border: "none",
            background: loading ? "#94a3b8" : "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
            color: "white", fontSize: 16, fontWeight: 700, cursor: loading ? "not-allowed" : "pointer",
          }}>
            {loading ? "Sauvegarde..." : "Sauvegarder"}
          </button>
        </div>
      </div>
    </div>
  );
}